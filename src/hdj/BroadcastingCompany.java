package hdj;

public enum BroadcastingCompany {

	KBS, SBS, MBC;
	
	public static void printBroad() {
	BroadcastingCompany [] list = BroadcastingCompany.values();
		
		for(int i = 0; i < list.length; i++) {
			System.out.print((i == 0 ? "" : ",") + list[i]);
		}
		System.out.println();
	}
	
	public static boolean check(String str) {
		try {
			return BroadcastingCompany.valueOf(str) != null;
		}catch(Exception e) {
			return false;
		}
	}
}
