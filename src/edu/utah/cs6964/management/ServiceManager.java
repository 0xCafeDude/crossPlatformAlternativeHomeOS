package edu.utah.cs6964.management;

import java.util.ArrayList;
import java.util.List;

import edu.utah.cs6964.api.Module;
import edu.utah.cs6964.management.access.AccessRule;
import javax.management.relation.Role;

public class ServiceManager {
	List<Module> systemModules = new ArrayList<Module>();
	private static ServiceManager serviceManagerInstance = new ServiceManager();
	
	private ServiceManager() {
		
	}
	
	public static ServiceManager getInstance() {
		return serviceManagerInstance;
	}
	
	public boolean addModule(Module module) {
		return systemModules.add(module);
	}
	
	public boolean removeModule(Module module) {
		return systemModules.remove(module);
	}
        
        public Role getRole(Module sender, String roleName)
        {
            
            for(int i = 0; i < systemModules.size(); ++i)
            {
                if(systemModules.get(i).getOfferedRoles().contains(roleName))
                {
                    ArrayList<AccessRule> rules = Core.getInstance().getBackend().getAccessRules();
                    
                    for(AccessRule rule : rules)
                    {
                        if(rule.getFromModule().equals(sender.getModuleId()) &&
                           rule.getToModule().equals(systemModules.get(i)) &&
                           Core.getInstance().getLoggedInUser().getGroups().contains(rule.getGroupID()) //&&
                           )
                        {
                            return (Role) systemModules.get(i);
                        }
                    }
                    break;
                }
            }
            return null;
        }
}
