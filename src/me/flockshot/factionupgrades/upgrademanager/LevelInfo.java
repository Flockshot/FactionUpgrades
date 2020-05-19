package me.flockshot.factionupgrades.upgrademanager;

public class LevelInfo
{
	private int level;
	private double cost;
	private double value;
	
	public LevelInfo(int level, double value) {
		this(level, value, 0);
	}
	
	public LevelInfo(int level,  double cost, double value)
	{
		setLevel(level);
		setValue(value);
		setCost(cost);
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
}
