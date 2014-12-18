package edu.utah.cs6964.management;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.utah.cs6964.management.access.AccessRule;
import edu.utah.cs6964.management.access.DayOfWeek;
import edu.utah.cs6964.management.access.Time;
import edu.utah.cs6964.modules.Module;

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
        
        public Module getRole(Module sender, String roleName)
        {
            Calendar cal = Calendar.getInstance();
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            
            DayOfWeek day;
            switch(dayOfWeek)
            {
                case 1:
                    day = DayOfWeek.Sunday;
                    break;
                case 2:
                    day = DayOfWeek.Monday;
                    break;
                case 3:
                    day = DayOfWeek.Tuesday;
                    break;
                case 4:
                    day = DayOfWeek.Wednesday;
                    break;
                case 5:
                    day = DayOfWeek.Thursday;
                    break;
                case 6:
                    day = DayOfWeek.Friday;
                    break;
                default:
                    day = DayOfWeek.Saturday;
            }
            
            for(int i = 0; i < systemModules.size(); ++i)
            {
                if(systemModules.get(i).getOfferedRoles().contains(roleName))
                {
                    ArrayList<AccessRule> rules = Core.getInstance().getBackend().getAccessRules();
                    
                    for(AccessRule rule : rules)
                    {
                        if(rule.getFromModule().equals(sender.getModuleId()) &&
                           rule.getToModule().equals(systemModules.get(i).getModuleId()) &&
                           Core.getInstance().getLoggedInUser().getGroups().contains(rule.getGroupID()) &&
                           rule.getDays().contains(day) &&
                           betweenTimes(new Time(hour, minute, second),
                                        rule.getStart(), rule.getEnd()))
                        {
                            return systemModules.get(i);
                        }
                    }
                    break;
                }
            }
            return null;
        }
        
        private boolean betweenTimes(Time current, Time start, Time end)
        {
            if(start.getHours() <= current.getHours() &&
               current.getHours() <= end.getHours() &&
               start.getMinutes() <= current.getMinutes() &&
               current.getMinutes() <= end.getMinutes() &&
               start.getSeconds() <= current.getSeconds() &&
               current.getSeconds() <= end.getSeconds())
            {
                return true;
            }
            return false;
        }
}
