package venus.simulator

/**
 * Thrown when errors occur during simulation.
 */
class SimulatorError : Throwable {
    /**
     * @param msg the message to error with
     */
    constructor(msg: String? = null) : super(msg)
}
