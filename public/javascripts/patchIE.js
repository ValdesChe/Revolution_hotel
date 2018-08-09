function IEFixedElementPos()
                {
                  // IE 7 en mode standard
                  if (window.ActiveXObject && window.XMLHttpRequest && window.external && document.compatMode=="CSS1Compat") 
                  {
                     return ("fixed");
                  }
                  return ("absolute");
                }
         
                function IEFixedElementTop(defaultTop)
                {
                  // IE 7 en mode standard
                  if (window.ActiveXObject && window.XMLHttpRequest && window.external && document.compatMode=="CSS1Compat") 
                  {
                     return (defaultTop.toString() + "px;")
                  }
                  return ((document.body.scrollTop+defaultTop) + "px");
                }
         
                function IEFixedElementLeft(defaultLeft) 
                {
                  // IE 7 en mode standard
                  if (window.ActiveXObject && window.XMLHttpRequest && window.external && document.compatMode=="CSS1Compat") 
                  {
                     return (defaultLeft.toString() + "px;")
                  }
                  return ((document.body.scrollLeft+defaultLeft) + "px");
                }
         
                function IE100Width() 
                {
                    //IE 7  en mode standard
                    if (window.ActiveXObject && window.XMLHttpRequest && window.external && document.compatMode=="CSS1Compat")
                    {
                        return "100%";
                    }
                    return (document.body.offsetWidth-21) + "px";
                }
         
        	function IE100Height() 
                {
                    //IE 7  en mode standard
                    if (window.ActiveXObject && window.XMLHttpRequest && window.external && document.compatMode=="CSS1Compat")
                    {
                        return "100%";
                    }
                    return (document.body.offsetHeight-21) + "px";
                }
