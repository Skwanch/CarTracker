/**************************
	Kevin Wang
	Project 3
	Min Priority Queue
**************************/
import java.util.*;

public class PriorityQueue
{
	private int max;		// maximum number of elements on PQ
    private int size;		// number of elements in PQ
    private int[] pq;		// binary heap using 1-based indexing
    private int[] qp;		// inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private Car[] cars;		// Cars[i] = priority of i

    public PriorityQueue(int max) 
	{
        if (max < 0) throw new IllegalArgumentException();
        this.max = max;
        cars = new Car[max + 1];    
        pq = new int[max + 1];
        qp = new int[max + 1];                   
        for (int i = 0; i <= max; i++)
		{
            qp[i] = -1;
		}
    }
	
	public int size() 
	{
        return size;
    }
	
    public boolean isEmpty() 
	{
        return size == 0;
    }

    public boolean contains(int i) 
	{
        if (i < 0 || i >= max) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }
	
    public void insertP(int i, Car car) 
	{
		if(size == max - 1)	resize();
        if (i < 0 || i >= max) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue: " + qp[i]);
        size++;
        qp[i] = size;
        pq[size] = i;
        cars[i] = car;
        swimPrice(size);
    }
	
    public void insertM(int i, Car car) 
	{
		if(size == max - 1) resize();
        if (i < 0 || i >= max) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue: " + qp[i]);
        size++;
        qp[i] = size;
        pq[size] = i;
        cars[i] = car;
        swimMileage(size);
    }
	
	private void resize()
	{
		int old_size = max;
		max = old_size * 2;
		Car[] temp_c = new Car[max + 1]; 
		int[] temp_pq = new int[max + 1];
		int[] temp_qp = new int[max + 1];
		
		for (int i = 0; i <= max; i++)
		{
            temp_qp[i] = -1;
		}
		for (int i = 1; i <= size; i++)
		{
			temp_c[i] = cars[i];
			temp_pq[i] = pq[i];
			temp_qp[i] = qp[i];
		}
		cars = temp_c;
		pq = temp_pq;
		qp = temp_qp;
	}
	
    public int minIndex() 
	{
        if (size == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }
	
    public Car minCar() 
	{
        if (size == 0) throw new NoSuchElementException("Priority queue underflow");
        return cars[pq[1]];
    }

    public int deleteMinPrice() 
	{
        if (size == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exch(1, size--);
        sinkPrice(1);
        qp[min] = -1;            // delete
        cars[pq[size + 1]] = null;    // to help with garbage collection
        pq[size + 1] = -1;            // not needed
        return min;
    }
    public int deleteMinMileage() 
	{
        if (size == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exch(1, size--);
        sinkMileage(1);
        qp[min] = -1;            // delete
        cars[pq[size + 1]] = null;    // to help with garbage collection
        pq[size + 1] = -1;            // not needed
        return min;
    }

    public Car carOf(int i) 
	{
        if (i < 0 || i >= max) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return cars[i];
    }

    public void changeCarP(int i, Car car) // Changes order using user input price
	{
        if (i < 0 || i >= max) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        cars[i] = car;
        swimPrice(qp[i]);
        sinkPrice(qp[i]);
    }
    public void changeCarM(int i, Car car)  // Changes order using user input mileage
	{
        if (i < 0 || i >= max) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        cars[i] = car;
        swimMileage(qp[i]);
        sinkMileage(qp[i]);
    }

    public void deleteP(int i) //Deletes price from queue
	{
        if (i < 0 || i >= max) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exch(index, size--);
        swimPrice(index);
        sinkPrice(index);
        cars[i] = null;
        qp[i] = -1;
    }

    public void deleteM(int i) //Deletes mileage from queue
	{
        if (i < 0 || i >= max) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exch(index, size--);
        swimMileage(index);
        sinkMileage(index);
        cars[i] = null;
        qp[i] = -1;
    }

    public Car findMinP (String make, String model) //Finds lowest price car using make and model
	{
		Car match = new Car();
		int next_level = 1;
        int car = 1;
 
        for(int j = 1;j <= next_level; ++j)
		{
			car = pq[j];
			if(j > size)
			{
				break;
            }
            if(cars[pq[j]].getMake().equals(make) && cars[pq[j]].getModel().equals(model))
			{
				car = pq[j];
				match = cars[car];
				break;
            }
          
			if (greaterP(car,j) )
			{
				next_level = 2 * j;
			}
			else
			{
				next_level = (2*j)+1;
			}
		}
		return match;
    }

    public Car findMinM (String make, String model) //Finds lowest mileage car using make and model
	{
		Car match = new Car();
		int next_level = 1;
        int car = 1;
		
		for(int j = 1;j <= next_level; ++j)
		{		
			car = pq[j];
			if(j > size)
			{
				break;
            }
            if(cars[pq[j]].getMake().equals(make) && cars[pq[j]].getModel().equals(model))
			{
				car = pq[j];
				match = cars[car];
				break;
            }
          
			if (greaterM(car,j))
			{
				next_level = 2 * j;
			}
			else
			{
				next_level = (2*j)+1;
			}
		}
		return match;
    }

    private boolean greaterP(int i, int j) // Compares prices
	{
        return cars[pq[i]].getPrice() > (cars[pq[j]]).getPrice();
    }
    private boolean greaterM(int i, int j) // Compares mileages
	{
        return cars[pq[i]].getMileage() > (cars[pq[j]]).getMileage();
    }

    private void exch(int i, int j) 
	{
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swimPrice(int k) 
	{
        while (k > 1 && greaterP(k/2, k)) 
		{
            exch(k, k/2);
            k = k/2;
        }
    }
    private void swimMileage(int k) 
	{
        while (k > 1 && greaterM(k/2, k)) 
		{
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sinkPrice(int k) 
	{
        while (2 * k <= size) 
		{
            int j = 2 * k;
            if (j < size && greaterP(j, j + 1)) j++;
            if (!greaterP(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
    private void sinkMileage(int k) 
	{
        while (2 * k <= size) 
		{
            int j = 2 * k;
            if (j < size && greaterM(j, j + 1)) j++;
            if (!greaterM(k, j)) break;
            exch(k, j);
            k = j;
        }
    }	
}


