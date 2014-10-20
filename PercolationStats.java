public class PercolationStats 
{
	private int T;
	private double mean, stddev;
	private double fraction[];
	
	/* perform T independent computational experiments on an N-by-N grid */
	public PercolationStats(int N, int T) 
	{
		// constructor throws a java.lang.IllegalArgumentException if either N ≤ 0 or T ≤ 0.
		if (N <= 0)
            throw new IllegalArgumentException();  
        if (T <= 0) 
            throw new IllegalArgumentException();  
        
        this.T = T; 
        fraction = new double[T];
        int experimentTimes = 0;  
        
        while (experimentTimes < T)
        {  
            Percolation percolation = new Percolation(N); 
            
            // the array flag checking if the site is open
            boolean[][] siteOpen = new boolean[N+1][N+1]; 
            
            // the number of open sites
            int openSitesCount = 0; 
            
            while(true)
            {  
                openSitesCount++;  
                while(true)
                {  
                	// randomly choose a site
                    int i = StdRandom.uniform(N) + 1;  
                    int j = StdRandom.uniform(N) + 1;  
                    
                    if (siteOpen[i][j]) 
                        continue;  
                    else
                    {  
                    	// open blocked sites
                        percolation.open(i, j);  
                        siteOpen[i][j] = true;  
                        break;  
                    }  
                }  
                
                // when the system percolates, then calculate the percolation threshold fraction
                if (percolation.percolates()){  
                    fraction[experimentTimes] = (double)openSitesCount / ((double)N * (double)N);  
                    break;  
                }  
            }  
            experimentTimes++;  
        }  
        
        // use StdStats APIs to calculate mean and standard deviation
        this.mean = StdStats.mean(fraction);  
        this.stddev = StdStats.stddev(fraction);  
	}
	
	/* sample mean of percolation threshold */
	public double mean()
	{
		return this.mean;
	}
	
	/* sample standard deviation of percolation threshold */
	public double stddev() 
	{
		return this.stddev;
	}
	
	/* returns lower bound of the 95% confidence interval */
	public double confidenceLo() 
	{
		return this.mean - 1.96 * this.stddev / Math.sqrt(T); 
	}
	
	/* returns upper bound of the 95% confidence interval */
	public double confidenceHi() 
	{
		return this.mean + 1.96 * this.stddev / Math.sqrt(T);
	}
	
	/* test client */
	public static void main(String[] args) 
	{
		int N = StdIn.readInt();    
        int T = StdIn.readInt();  
        
        Stopwatch timing = new Stopwatch(); 
        PercolationStats percolationStats = new PercolationStats(N, T);  
        double runningTime = timing.elapsedTime();  
        
        System.out.println("time                    = " + runningTime);  
        System.out.println("mean                    = " + percolationStats.mean());  
        System.out.println("stddev                  = " + percolationStats.stddev());  
        System.out.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());  
	}

}
