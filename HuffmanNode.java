class HuffmanNode implements Comparable<HuffmanNode> {
    int frequency;
    char data;
    HuffmanNode left, right;

    public HuffmanNode(int frequency, char data) {
        this.frequency = frequency;
        this.data = data;
        left = right = null;
    }

    public HuffmanNode(char frequency) {
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}