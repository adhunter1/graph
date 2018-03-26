package cs241Proj03;

import java.util.ArrayList;

class City {
	int cityNum;
	String cityCode;
	String fullName;
	long population;
	int elevation;
	ArrayList<Road> roads; 

	public City(int cNum, String cCode, String fName) {
		cityNum = cNum;
		cityCode = cCode;
		fullName = fName;
		roads = new ArrayList<Road>();
	}

	public void setCityNum(int cN) {
		cityNum = cN;
	}

	public int getCityNum() {
		return cityNum;
	}

	public void setCityCode(String cC) {
		cityCode = cC;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setFullName(String f) {
		fullName = f;
	}

	public String getFullName() {
		return fullName;
	}

	public void setPopulation(long p) {
		population = p;
	}

	public long getPopulation() {
		return population;
	}

	public void setElevation(int e) {
		elevation = e;
	}

	public int getElevation() {
		return elevation;
	}

	public void addRoad(Road road) {
		roads.add(road);
	}

	public void removeRoad(Road road) {
		roads.remove(road);
	}
}
