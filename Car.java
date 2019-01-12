/*******************
	Kevin Wang
	Project 3
	Car class
*******************/
public class Car
{
	private String vin;
	private String make;
	private String model;
	private double price;
	private int mileage;
	private String color;
	
	public Car()
	{
		vin = null;
		make = null;
		model = null;
		price = 0;
		mileage = 0;
		color = null;
	}

	public Car(String v, String ma, String mo, double p, int mi, String c)
	{
		vin = v;
		make = ma;
		model = mo;
		price = p;
		mileage = mi;
		color = c;
	}

	public String getVin()
	{
		return vin;
	}

	public void setVin(String v)
	{
		vin = v;
	}

	public String getMake()
	{
		return make;
	}

	public void setMake(String ma)
	{
		make = ma;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String mo)
	{
		model = mo;
	}
	///////////////////////////////
	public String getMakeModel()
	{
		String make_model = make + model;
		return make_model;
	}
	///////////////////////////////
	public double getPrice()
	{
		return price;
	}

	public void setPrice(double p)
	{
		price = p;
	}

	public int getMileage()
	{
		return mileage;
	}

	public void setMileage(int mi)
	{
		mileage = mi;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String c)
	{
		color = c;
	}
	
	public void display() // Prints out car info to user
	{
		if (vin == null)
		{
			System.out.println("\nNO MATCH FOUND\n");
		}
		else
		{
			System.out.println("\nRECOMMENDED CAR:");
			System.out.printf("VIN:\t\t%s\n", vin);
			System.out.printf("Make:\t\t%s\n", make);
			System.out.printf("Model:\t\t%s\n", model);
			System.out.printf("Price:\t\t%.2f\n", price);
			System.out.printf("Mileage:\t%d\n", mileage);
			System.out.printf("Color:\t\t%s\n\n", color);
		}
	}
}
