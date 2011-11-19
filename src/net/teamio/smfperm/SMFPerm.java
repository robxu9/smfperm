package net.teamio.smfperm;

import lib.PatPeter.SQLibrary.MySQL;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import net.teamio.ThreadHelper;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.ConfigurationException;

public class SMFPerm extends JavaPlugin{
	
	protected final ThreadHelper th = new ThreadHelper("SMFCon");
	protected MySQL connection;
	protected static Permission permission = null;
	protected static Chat chat = null;

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		th.print("Starting up SMFPerm version " + this.getDescription().getVersion() + "...",0);
		if (!setupPermission()){
			th.print("Vault failed to setup permissions, disabling.",-1);
			onDisable();
		}
		else {
			th.print("Loaded Permissions.", 0);
			try {
				if (setupDefaults()){
					th.print("New configuration has been written, disabling to avoid errors.",1);
					th.print("Modify the new configuration and restart the server to load.",1);
					onDisable();
				}
				else{
					th.print("Loaded configuration.", 0);
					
				}
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				th.print("Could not write/load configuration! Do you have permissions?", -1);
				onDisable();
			}
		}
		
	}
	
	
	private boolean setupPermission(){
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}

		return (permission != null);
	}
	
	private boolean setupDefaults() throws ConfigurationException{

		boolean setup = false;
		
		/* mysql configuration */
		try{
			th.actMySQLFile(false);
			if (!th.mysqlconfig.contains("prefix")){
				th.mysqlconfig.set("prefix", "smf_");
				setup = true;
			}
			if (!th.mysqlconfig.contains("hostname")){
				th.mysqlconfig.set("hostname", "localhost");
				setup = true;
			}
			if (!th.mysqlconfig.contains("port")){
				th.mysqlconfig.set("port", "3306");
				setup = true;
			}
			if (!th.mysqlconfig.contains("database")){
				th.mysqlconfig.set("database", "forum");
				setup = true;
			}
			if (!th.mysqlconfig.contains("username")){
				th.mysqlconfig.set("username", "root");
				setup = true;
			}
			if (!th.mysqlconfig.contains("password")){
				th.mysqlconfig.set("password", "toor");
				setup = true;
			}
			th.actMySQLFile(true);
		}catch(Exception e1){
			e1.printStackTrace();
			throw new ConfigurationException("Could not set defaults!");
		}
		
		/* defaults configuration */
		try{
			th.actDefaultsFile(false);
			if (!th.defaults.contains("tabletocheck")){
				th.defaults.set("tabletocheck","members");
				setup = true;
			}
			if (!th.defaults.contains("username_field")){
				th.defaults.set("username_field","member_name");
				setup = true;
			}
			if (!th.defaults.contains("displayname_field")){
				th.defaults.set("displayname_field", "real_name");
				setup = true;
			}
			th.actDefaultsFile(true);
		}catch(Exception e1){
			e1.printStackTrace();
			throw new ConfigurationException("Could not set defaults!");
		}

		return setup;
	}
	
	private boolean setupMySQL() {
		connection = new MySQL(th.getLog(), th.mysqlconfig.getString("prefix"),
				th.mysqlconfig.getString("hostname"),
				th.mysqlconfig.getString("port"),
				th.mysqlconfig.getString("database"),
				th.mysqlconfig.getString("username"),
				th.mysqlconfig.getString("password"));
		return connection.checkConnection();
	}
	
	

}
