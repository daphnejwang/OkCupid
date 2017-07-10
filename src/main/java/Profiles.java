import java.util.ArrayList;
import java.util.List;

/***
 * The Profiles object contains a list of all profiles.
 * 
 * @author dwang1
 *
 */

public class Profiles {
	List<Profile> profiles = new ArrayList<>();
	
	public Profiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
}
