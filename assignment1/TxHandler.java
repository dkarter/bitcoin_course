import java.util.ArrayList;

public class TxHandler {

  private UTXOPool utxoPool;

  /**
   * Creates a public ledger whose current UTXOPool (collection of unspent
   * transaction outputs) is {@code utxoPool}. This should make a copy of
   * utxoPool by using the UTXOPool(UTXOPool uPool) constructor.
   */
  public TxHandler(UTXOPool utxoPool) {
    // IMPLEMENT THIS
    this.utxoPool = new UTXOPool(utxoPool);
  }

  /**
   * @return true if:
   * (1) all outputs claimed by {@code tx} are in the current UTXO pool,
   * (2) the signatures on each input of {@code tx} are valid,
   * (3) no UTXO is claimed multiple times by {@code tx},
   * (4) all of {@code tx}s output values are non-negative, and
   * (5) the sum of {@code tx}s input values is greater than or equal to the sum
   *     of its output values;
   *  and false otherwise.
   */
  public boolean isValidTx(Transaction tx) {
    // IMPLEMENT THIS

    // QUESTIONS:
   //  * (2) the signatures on each input of {@code tx} are valid,
   //    - the public key seems to be stored on the output as address and not on
   //    the inputs which contradicts the lecture. I've taken it from the output
   //    for this task but it seems wrong? Or maybe I misunderstood.
    // * (3) no UTXO is claimed multiple times by {@code tx},
    //  - where in the code is transaction claiming to have utxos at all?
   // * (5) the sum of {@code tx}s input values is greater than or equal to the sum
   // *     of its output values;
   //   - inputs don't seem to have a value, what do you mean by input values?
   //   I'm assuming that by output values you mean the `double value` on outputs
    ArrayList<Transaction.Output> transOutputs = tx.getOutputs();
    ArrayList<Transaction.Input> transInputs = tx.getInputs();

    //check all outputs are in pool
    if (!TransactionVerifier.checkAllOutputsExist(transOutputs, this.utxoPool))
      return false;

    // verify all signatures
    if (!TransactionVerifier.verifySignatures(tx, transInputs))
      return false;

    // verify all outputs have a positive value
    if (!TransactionVerifier.checkAllOutputsHavePositiveAmounts(transOutputs))
      return false;

    // verify sum of inputs is greater or equal to sum of outputs
    if (!TransactionVerifier.checkSumOfInputsAndOutputs(transInputs, transOutputs))
      return false;

    return true;
  }



  /*
   * Handles each epoch by receiving an unordered array of proposed
   * transactions, checking each transaction for correctness, returning a
   * mutually valid array of accepted transactions, and updating the current
   * UTXO pool as appropriate.
   */
  public Transaction[] handleTxs(Transaction[] possibleTxs) {
    // IMPLEMENT THIS
    return new Transaction[0];
  }

}
