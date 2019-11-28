import fr.dgac.ivy.Ivy;


public class Main {
	
	public static void main(String[] args)
	{
		
	}
	
	
	public String getActionFromEnum(m_EAction action)
	{
		String m_strAction = "" ; 
		switch(action){
		case CREATE	: m_strAction = "CREATE" ; break ;  
		case DELETE	: m_strAction = "DELETE" ; break ; 
		case REPLACE: m_strAction = "REPLACE" ; break ; 
		default: break ; 
		}
		return m_strAction;		
	}

	public m_EAction getEnumFromActionString(String m_strAction)
	{
		m_EAction action = null ; 
		if(m_strAction == "CREATE")
			action = m_EAction.CREATE ; 
		if(m_strAction == "DELETE")
			action = m_EAction.DELETE ; 
		if(m_strAction == "REPLACE")
			action = m_EAction.REPLACE ; 
		
		return action ; 
	}
}
