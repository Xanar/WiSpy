
import Calculate.*;

public class mloc {
	
	public static void main(String[] args) {
		String[] bssid=new String[]{"","00:1b:57:fc:6f:01","00:1b:57:fc:6f:02","00:1b:57:fc:6f:04"};
		Calculate x=new Calculate();
		System.out.println("Input:");
		for(int i=1;i<=3;i++)
			System.out.println(bssid[i]+" , ");
		x.findintersection();
		x.calcpos(3,bssid);
	}

}
