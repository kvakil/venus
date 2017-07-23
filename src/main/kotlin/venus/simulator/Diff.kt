package venus.simulator

interface Diff {
    operator fun invoke(state: SimulatorState)
}