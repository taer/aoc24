enum class Direction(val deltaRow: Int, val deltaCol: Int) {
    N(-1, 0),
    S(1, 0),
    E(0, 1),
    W(0, -1),
    NW(-1, -1),
    NE(-1, 1),
    SE(1, 1),
    SW(1, -1),
}