package application;

public class Service {
	private int timeKey;
	private String name;

	public Service(String name, int key) {
		timeKey = key;
		this.name = name;
	}

	public void setTimeKey(int k) {
		timeKey = k;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTimeKey() {
		return timeKey;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		if (getTimeKey() == 1) {
			return getName() + " (15 min)";
		} else if (getTimeKey() == 2) {
			return getName() + " (30 min)";
		} else if (getTimeKey() == 3) {
			return getName() + " (45 min)";
		} else {
			return getName() + " (1 hour)";
		}

	}
}
