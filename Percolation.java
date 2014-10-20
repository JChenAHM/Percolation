public class Percolation 
{
	private boolean[] siteOpenState;  
    private WeightedQuickUnionUF checkConnectState;  
	private int N;  
      
    /* create N-by-N grid, with all sites blocked */ 
    public Percolation(int N) 
    {  
    	// constructor throws an IllegalArgumentException if N ≤ 0
    	if (N <= 0)   
            throw new IllegalArgumentException(); 
    	
        this.N = N; 
        
        // a boolean n-by-n array declaration
        siteOpenState = new boolean[N * N];  
        for (int i = 0; i < N; i++)  
            for (int j = 0; j < N; j++) 
            	// all sites initialized as blocked
                siteOpenState[i * N + j] = false;  
        
        // With virtual top and bottom sites, declare a data structure of (N-by-N)+2 objects to check top-bottom connection.
        checkConnectState = new WeightedQuickUnionUF(N * N + 2); 
    } 
    
    /* open site (row i, column j) if it is not already */
    public void open(int i, int j)
    {
    	// Throw an IndexOutOfBoundsException if argument to open() is outside its prescribed range
    	if (i < 1 || i > N)
            throw new IndexOutOfBoundsException("row " + i + " is out of bounds");   
        if (j < 1 || j > N) 
            throw new IndexOutOfBoundsException("row " + j + " is out of bounds"); 
        
        // open sites and union the sites with checking the open state of up, down, left, right
        if (!siteOpenState[(i - 1) * N + (j - 1)])  
        {  
            // if blocked, then open
            siteOpenState[(i - 1) * N + (j - 1)] = true;
            
            // check the up-site, union
            if ((i - 1) >= 0 && j >= 0 && (i - 1) < N && j < N && siteOpenState[(i - 1) * N + j] && (i - 1) != N - 1) 
                checkConnectState.union((i - 1) * N + j, (i - 1) * N + j + 1);  
     
            // check the down-site, union
            if ((i - 1) >= 0 && (j - 2) >= 0 && (i - 1) < N && (j - 1) < N && siteOpenState[(i - 1) * N + j - 2] && (i - 1) != 0)
            	checkConnectState.union((i - 1) * N + j, (i - 1) * N + (j - 2) + 1);  
         
            // check the left-site, union
            if ((i - 2) >= 0 && (j - 1) >= 0 && (i - 2) < N && (j - 1) < N && siteOpenState[(i - 2) * N + j - 1])  
            	checkConnectState.union((i - 1) * N + j, (i - 2) * N + j);  
 
            // check the right-site, union
            if (i >= 0 && (j - 1) >= 0 && i < N && (j - 1) < N && siteOpenState[i * N + j - 1]) 
            	checkConnectState.union((i - 1) * N + j, i * N + j);  
       
            // connect the virtual top site to the top row
            if (i == 1)
            	checkConnectState.union(j, 0);  
            
            // connect the virtual bottom site to the bottom row
            if (i == N) 
            	checkConnectState.union((N - 1) * N + j, N * N + 1);  
        }
    }
        
    /* is site (row i, column j) open? */
    public boolean isOpen(int i, int j)
    {
    	// Throw an IndexOutOfBoundsException if argument to isopen() is outside its prescribed range
        if (i < 1 || i > N)
            throw new IndexOutOfBoundsException("row " + i + " is out of bounds");  
        if (j < 1 || j > N) 
            throw new IndexOutOfBoundsException("row " + j + " is out of bounds");  
            
        // return if the site is open
        return (siteOpenState[(i - 1) * N + j - 1]); 
    }
    
    /* is site (row i, column j) full? */
    public boolean isFull(int i, int j)
    {  
    	// Throw an IndexOutOfBoundsException if argument to isopen() is outside its prescribed range
        if (i < 1 || i > N)
            throw new IndexOutOfBoundsException("row " + i + " is out of bounds");  
        if (j < 1 || j > N) 
            throw new IndexOutOfBoundsException("row " + j + " is out of bounds");  
        
        // return if the site is full
        return siteOpenState[(i - 1) * N + j - 1] && checkConnectState.connected((i - 1) * N + j, 0); 
    }
    
    /* does the system percolate? */
    public boolean percolates()
    {
    	return checkConnectState.connected(0, N * N + 1);
    }       
}
