
public class Commande {
	
	private String m_strAction ; 
	private Object m_Object ; 
	
	public Commande(String action, Object objet)
	{
		m_strAction  = action ; 
		setM_Object(objet) ; 
	}


	public String getM_strAction() {
		return m_strAction;
	}


	public void setM_strAction(String m_strAction) {
		this.m_strAction = m_strAction;
	}


	public Object getM_Object() {
		return m_Object;
	}


	public void setM_Object(Object m_Object) {
		this.m_Object = m_Object;
	}
	
	
	public boolean compute(String action)
	{
		boolean m_computeDone = false ;
		m_EAction enumAction = getEnumFromActionString(action) ; 
		switch (enumAction) {
		case CREATE:
			
			break;
		case DELETE: 
			m_computeDone = deleteObject(m_Object); 
			break; 
			
		case REPLACE:
			break ; 

		default:
			break;
		}	
		
		return m_computeDone ;
	}
	

	public boolean deleteObject(Object objet)
	{
		boolean m_createDone = false ; 
		
		
		
		
		
		return m_createDone ; 
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
