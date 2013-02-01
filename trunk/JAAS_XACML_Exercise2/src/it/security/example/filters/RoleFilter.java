package it.security.example.filters;


import it.security.example.metadata.UserMetaData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.ctx.Result;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.impl.CurrentEnvModule;
import com.sun.xacml.finder.impl.FilePolicyModule;

/**
 * Servlet Filter implementation class RoleFilter
 */
public class RoleFilter implements Filter {
	File[] listaFile;//contiene le policy disponibili

	//Pako
//	String POLICY_PATH="/Users/pasqualederosa/Documents/workspaceSDCS/";
	
	//Mario
	String POLICY_PATH="/Users/mario_fio/Documents/workspace-security/";
	
	//Giancarlo
//	String POLICY_PATH="/home/giancarlo/Scrivania/Cioppy/Università/Magistrale/Applicazioni Telematiche/workspace2/";
	
    /**
     * Default constructor. 
     */
    public RoleFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		File f;
        String policyfile;
        FilePolicyModule policyModule = new FilePolicyModule();
        PolicyFinder policyFinder = new PolicyFinder();
        Set policyModules = new HashSet();
      
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
       
        HttpSession session = req.getSession();
        if(session.getAttribute(UserMetaData.USERS_ROLE)!=null){
	        
        	String PATH_POLICY = POLICY_PATH+"JAAS_XACML_Exercise2/WebContent/policies/";//path della cartella contenente le policy
	        cercaPolicyFile(new File(PATH_POLICY));
	        
	        for(int i=0;i<listaFile.length;i++)
	        {
	             f=listaFile[i];
	             if(!f.getAbsolutePath().contains(".DS") && !f.getAbsolutePath().contains(".svn")){
	             policyfile = f.getAbsolutePath();
	             policyModule.addPolicy(policyfile); //aggiunge solo il nome del file
	             policyModules.add(policyModule);
	             policyFinder.setModules(policyModules);
	             }
	        }
	
	        CurrentEnvModule envModule = new CurrentEnvModule();
	        AttributeFinder attrFinder = new AttributeFinder();
	        List attrModules = new ArrayList();
	        attrModules.add(envModule);
	        attrFinder.setModules(attrModules);
	        
	        
	        try {
	        RequestCtx XACMLrequest = RequestBuilder.createXACMLRequest(req);
	  
	
	        PDP pdp = new PDP(new PDPConfig(attrFinder, policyFinder, null));
	
	        ResponseCtx XACMLresponse = pdp.evaluate(XACMLrequest);
	        
	        Set ris_set = XACMLresponse.getResults();
	        Result ris = null;
	        Iterator it = ris_set.iterator();
	
	        while (it.hasNext()) {
	            ris = (Result) it.next();
	        }
	        int dec = ris.getDecision();
	
	        if (dec == 0) {//permit
	            chain.doFilter(request, response);
	        } else if (dec == 1) {//deny
	            res.sendRedirect(req.getContextPath()+"/public/AccessDenied.jsp");
	        } else if (dec == 2||dec==3) {//indeterminate o not applicable
	            res.sendRedirect(req.getContextPath()+"/public/error.jsp"); 
	        }            }
	     catch (Exception ex) {
	        ex.printStackTrace();
	    }
        }
        //se richiedo direttamente il path riservata/* il filtro restituisce il controllo alla 
        //pagina, e in tal caso entra in gioco il security constraint definito in web.xml, per cui
        //devo comunque autenticarmi prima
        else
        	res.sendRedirect(req.getContextPath()+"/public/login.jsp");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	private void cercaPolicyFile (File dir) {
	      listaFile = dir.listFiles();  
	  }

}
