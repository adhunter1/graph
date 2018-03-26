package cs241Proj03;


public class Road {
	City distFromCity;
	City distToCity;
	int distance;

	public Road(City fromCity, City toCity, int d) {
		distFromCity = fromCity;
		distToCity = toCity;
		distance = d;

	}

	public void setDistFromCity(City fromCity) {
		distFromCity = fromCity;
	}

	public City getDistFromCity() {
		return distFromCity;
	}

	public void setDistToCity(City toCity) {
		distToCity = toCity;
	}

	public City getDistToCity() {
		return distToCity;
	}

	public void setDistance(int d) {
		distance = d;
	}

	public int getDistance() {
		return distance;
	}

}
