package abstractionBot;

public interface SafetyLogout {

	void logoutIfHealthIsLowAndNoFlaskChargesArePresent();
	
	boolean lastFlaskChargeExpended();
}
