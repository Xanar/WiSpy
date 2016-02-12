package Calculate;

public class Calculate {
	float x[] = {0, 20, 47, 69, 90, 130, 80 }, y[] = {0, 30, 56, 70, 45, 100, 80 }, ix[], iy[], locx, locy;
	int n = 6, ni, m, id[], r[] = {0, 46, 46, 46, 46, 46, 70 };
	String[] routerid=new String[]{"","00:1b:57:fc:6f:01","00:1b:57:fc:6f:02","00:1b:57:fc:6f:03","00:1b:57:fc:6f:04","00:1b:57:fc:6f:05","00:1b:57:fc:6f:06"};

	public void findintersection() {
		float dx, dy, dist;
		double a, h, cx, cy;
		int k = 0;
		ix = new float[61];
		iy = new float[61];
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++) {
				if (i == j)
					continue;
				// Find the distance between the centers.
				dx = x[i] - x[j];
				dy = y[i] - y[j];
				dist = (float) Math.sqrt(dx * dx + dy * dy);

				// See how many solutions there are.
				// (R0-R1)^2 <= (x0-x1)^2+(y0-y1)^2 <= (R0+R1)^2
				if ((dist >= Math.round(r[i] - r[j])) && (dist <= (r[i] + r[j])))
				{
					
					// Find a and h.
					a = (r[i] * r[i] - r[j] * r[j] + dist * dist) / (2 * dist);
				h = Math.sqrt(r[i] * r[i] - a * a);

				// Find P2.
				cx = x[i] + a * (x[j] - x[i]) / dist;
				cy = y[i] + a * (y[j] - y[i]) / dist;
				//System.out.println("i="+i+",j="+j+",k="+k);
				if (dist == r[i] + r[j]) {
					k++;
					ix[k] = (float) (cx + h * (y[j] - y[i]) / dist);
					iy[k] = (float) (cy - h * (x[j] - x[i]) / dist);
				} 
				else 
				{
					k++;
					ix[k] = (float) (cx + h * (y[j] - y[i]) / dist);
					iy[k] = (float) (cy - h * (x[j] - x[i]) / dist);
					k++;
					ix[k] = (float) (cx - h * (y[j] - y[i]) / dist);
					iy[k] = (float) (cy + h * (x[j] - x[i]) / dist);
				}
				
			}
	}ni=k;
	}


	public void calcpos(int m,String[] bssid) {
		int t = 0,count=0;
		float dx, dy, dist, sumx = 0, sumy = 0;
		id = new int[m + 1];
		//router indexes
		for (int i=1;i<=m;i++)
			for(int j=1;j<=6;j++)
				if(bssid[i].equals(routerid[j])){
					id[count]=j;
					count++;
					break;
				}
		for (int i = 0; i < ni; i++)
			for (int j = 1; j <= m; j++) {
				dx = ix[i] - x[id[j]];
				dy = iy[i] - y[id[j]];
				dist = (float) Math.sqrt(dx * dx + dy * dy);
				//dist=Math.round(dist);
				if (dist <= r[id[j]]) {
					sumx += ix[i];
					sumy += iy[i];
					t++;
				}

			}
		locx = sumx / t;
		locy = sumy / t;
		System.out.println("Location=(" + locx+","+locy+")");
	}

}
