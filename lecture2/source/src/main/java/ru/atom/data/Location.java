package ru.atom.data;

/**
 * Location data.
 * Immutable.
 *
 * units: degrees and meters
 */
class Location {
    private static final double EARTH_RADIUS = 6371e3;

    private final double latitude;
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Calculate distance to destination.
     *
     * @see  http://www.movable-type.co.uk/scripts/latlong.html
     *
     * @param location of destination point
     * @return distance to destination location
     */
    public double distanceTo(Location destination) {
        double phi1 = Math.toRadians(latitude);
        double phi2 = Math.toRadians(destination.latitude);
        double deltaPhi = Math.toRadians(destination.latitude - latitude);
        double deltaLamda = Math.toRadians(destination.longitude - longitude);

        double a = Math.pow(Math.sin(deltaPhi / 2), 2)
                + Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(deltaLamda / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * EARTH_RADIUS;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}