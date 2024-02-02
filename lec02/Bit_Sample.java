package lec02;

public class Bit_Sample {

	int getBit(int X, int k) {
		return (X >> k) & 1;
	}

	int clearBit(int X, int k) {
		return X & (~(1 << k));
	}

	int setBit(int X, int k) {
		return X | (1 << k);
	}

	int flipBit(int X, int k) {
		return X ^ (1 << k);
	}
}