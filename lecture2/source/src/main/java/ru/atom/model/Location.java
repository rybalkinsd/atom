package ru.atom.model;

/**
 * Location data.
 * Immutable.
 *
  */
public class Location {
    private static final double EARTH_RADIUS = 6371e3;

    /**
     * unit: degrees
     */
    private final double latitude;

    /**
     * unit: degrees
     */
    private final double longitude;

    public Location() {
        this(0, 0);
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Calculate distance to destination.
     *
     * @see  http://www.movable-type.co.uk/scripts/latlong.html
     *
     * all in radians
     * a = sin^2(dPhi / 2) + cos(phi1) * cos(phi2) * sin^2(dLambda / 2)
     * c = 2 * atan2(sqrt(a), sqrt(1 - a))
     * distance = c * R
     *
     * @param location of destination point
     * @return distance to destination location
     */
    public double distanceTo(Location destination) {
        double phi1 = Math.toRadians(latitude);
        double phi2 = Math.toRadians(destination.latitude);
        double deltaPhi = phi2 - phi1;
        double deltaLamda = Math.toRadians(destination.longitude - longitude);

        // return some random
        double a = Math.pow(Math.sin(deltaPhi / 2), 2)
                + Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(deltaLamda / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * EARTH_RADIUS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.latitude, latitude) != 0) return false;
        return Double.compare(location.longitude, longitude) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


}