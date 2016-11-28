import java.util.ArrayList;

public class TransactionVerifier {
  public static boolean verifySignatures(Transaction tx, ArrayList<Transaction.Input> transInputs) {
    // return transInputs
    //   .parallelStream()
    //   .allMatch((ti) -> Crypto.verifySignature(, tx.getRawDataToSign(ti.index), ti.signature));
    // can modify the `getRawDataToSign` to use 
    for (int i = 0; i < transInputs.size(); i++) {
      Transaction.Input in = transInputs.get(i);
      Transaction.Output out = tx.getOutput(in.outputIndex);
      if (!Crypto.verifySignature(out.address, tx.getRawDataToSign(i), in.signature)) {
        return false;
      }
    }

    return true;
  }

  public static boolean checkAllOutputsExist(ArrayList<Transaction.Output> transOutputs, UTXOPool pool) {
    ArrayList<UTXO> unspentTransactions = pool.getAllUTXO();

    for (int i = 0; i < unspentTransactions.size(); i++) {
      Transaction.Output poolOutput = pool.getTxOutput(unspentTransactions.get(i));
      Transaction.Output transactionOutput = transOutputs.get(i);
      if (!poolOutput.equals(transactionOutput))
        return false;
    }

    return true;
  }

  public static boolean checkAllOutputsHavePositiveAmounts(ArrayList<Transaction.Output> transOutputs) {
    return transOutputs
      .parallelStream()
      .allMatch((out) -> out.value > 0);
  }

  public static boolean checkAllOutputsHavePositiveAmounts(ArrayList<Transaction.Output> transOutputs, ArrayList<Transaction.Input> transInputs) {
    double outputsSum = transOutputs
      .parallelStream()
      .mapToDouble(out -> out.value)
      .sum();

    double inputsSum = transInputs
      .parallelStream()
      .mapToDouble(in -> in.value)
      .sum();

    return inputsSum >= outputsSum;
  }
}
