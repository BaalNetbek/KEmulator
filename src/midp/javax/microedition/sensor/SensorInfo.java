package javax.microedition.sensor;

public interface SensorInfo {
	public static final int CONN_EMBEDDED = 1;
	public static final int CONN_REMOTE = 2;
	public static final int CONN_SHORT_RANGE_WIRELESS = 4;
	public static final int CONN_WIRED = 8;
	public static final String CONTEXT_TYPE_AMBIENT = "ambient";
	public static final String CONTEXT_TYPE_DEVICE = "device";
	public static final String CONTEXT_TYPE_USER = "user";
	public static final String CONTEXT_TYPE_VEHICLE = "vehicle";
	public static final String PROP_LATITUDE = "latitude";
	public static final String PROP_LOCATION = "location";
	public static final String PROP_LONGITUDE = "longitude";
	public static final String PROP_MAX_RATE = "maxSamplingRate";
	public static final String PROP_VENDOR = "vendor";
	public static final String PROP_VERSION = "version";
	public static final String PROP_IS_CONTROLLABLE = "controllable";
	public static final String PROP_IS_REPORTING_ERRORS = "errorsReported";

	ChannelInfo[] getChannelInfos();

	int getConnectionType();

	String getContextType();

	String getDescription();

	int getMaxBufferSize();

	String getModel();

	Object getProperty(final String p0);

	String[] getPropertyNames();

	String getQuantity();

	String getUrl();

	boolean isAvailabilityPushSupported();

	boolean isAvailable();

	boolean isConditionPushSupported();
}
