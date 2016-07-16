class Main {
	public static void main(String[] args) {
			int changeCoins = 0;
			int average = 0;
			int divide = 0;
			for(int i = 0; i < 100; i++){
				changeCoins = i;
				while(changeCoins != 0) {
					System.out.println(changeCoins);
					if((changeCoins - 25) >= 0) {
						changeCoins -= 25;
						average++;
						System.out.println(changeCoins);
					}
					else if((changeCoins - 10) >= 0) {
						changeCoins -= 10;
						average++;
					}
					else if((changeCoins - 1) >= 0) {
						changeCoins -= 1;
						average++;
					}
				}
				divide++;
			}
			System.out.println((double)average/divide);
	}
}
