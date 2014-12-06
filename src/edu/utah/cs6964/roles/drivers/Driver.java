
package edu.utah.cs6964.roles.drivers;

import edu.utah.cs6964.exceptions.ModuleNotStartedException;
import edu.utah.cs6964.roles.devices.Device;

/**
 *
 * @author christopher
 */
public interface Driver {
    public boolean sendData(Device d, byte[] bytes) throws ModuleNotStartedException;
    public String getProtocolRole();
    public String getId();
    public void setId(String id);
}
