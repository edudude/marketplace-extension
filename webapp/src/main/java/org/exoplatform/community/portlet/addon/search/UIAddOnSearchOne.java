/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.community.portlet.addon.search;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.exoplatform.addon.service.AddOnService;
import org.exoplatform.addon.service.Addon;
import org.exoplatform.community.portlet.addon.UIAddOnWizard;
import org.exoplatform.portal.mop.SiteType;
import org.exoplatform.portal.webui.container.UIContainer;
import org.exoplatform.portal.webui.util.Util;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.wcm.friendly.FriendlyService;
import org.exoplatform.services.wcm.utils.WCMCoreUtils;
import org.exoplatform.web.url.navigation.NavigationResource;
import org.exoplatform.web.url.navigation.NodeURL;
import org.exoplatform.webui.application.StateManager;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIPopupContainer;
import org.exoplatform.webui.core.lifecycle.Lifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


@ComponentConfig(
        lifecycle = Lifecycle.class,
        template = "app:/templates/AddOnSearchPortlet/UIAddOnSearchOne.gtmpl",
        events = {
                @EventConfig(listeners = UIAddOnSearchOne.EditActionListener.class),
                @EventConfig(listeners = UIAddOnSearchOne.DetailActionListener.class),
                @EventConfig(listeners = UIAddOnSearchOne.InstallActionListener.class),
                @EventConfig(listeners = UIAddOnSearchOne.UnInstallActionListener.class)
        }
)
public class UIAddOnSearchOne extends UIContainer {

    private String nodeId;


    public static final String INSTALL = "install";

    public static final String UNINSTALL = "uninstall";

    public static final String ADDON_SCRIPT_LINUX = "/home/exo/Desktop/MarcketPlae/platform-4.4.0/addon"; // to be updated : tomcatHome.


    private static final Log log = ExoLogger.getLogger(UIAddOnSearchOne.class);

    public UIAddOnSearchOne() throws Exception {

    }

    public void setNodeId(String id) {
        this.nodeId = id;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public Addon getNode() throws PathNotFoundException, RepositoryException {
        return AddOnService.getNodeById(this.getNodeId());
    }

    public String getImageCover() throws PathNotFoundException, RepositoryException {

        return this.getNode().getScreenshotUrl();

    }

    /*public String getStrProperty(String propertyName) throws RepositoryException {

        return AddOnService.getStrProperty(this.getNode(), propertyName);

    }*/

    public Boolean canEdit() throws RepositoryException {

       /* String userId = Util.getPortalRequestContext().getRemoteUser();
        String ownerid = this.getStrProperty("exo:owner");

        if (userId != null && ownerid != null && userId.equals(ownerid)) {
            return true;
        }
        return false;*/
       return true;
    }
    public Boolean canInstall() throws RepositoryException {
        return AddOnService.getNodeById(nodeId).isInstalled();
    }

    /*public String getURL() throws Exception {
        Node node = this.getNode();
        String repository = WCMCoreUtils.getRepository().getConfiguration().getName();
        String workspace = node.getSession().getWorkspace().getName();
        String basePath = "addon-detail";
        String detailParameterName = "content-id";

        StringBuffer path = new StringBuffer();
        path.append("/").append(repository).append("/").append(workspace);
        NodeURL nodeURL = Util.getPortalRequestContext().createURL(NodeURL.TYPE);
        NavigationResource resource = new NavigationResource(SiteType.PORTAL,
                Util.getPortalRequestContext()
                        .getPortalOwner(), basePath);
        nodeURL.setResource(resource);
        if (node.isNodeType("nt:frozenNode")) {
            String uuid = node.getProperty("jcr:frozenUuid").getString();
            Node originalNode = node.getSession().getNodeByUUID(uuid);
            path.append(originalNode.getPath());
            nodeURL.setQueryParameterValue("version", node.getParent().getName());
        } else {
            path.append(node.getPath());
        }

        nodeURL.setQueryParameterValue(detailParameterName, path.toString());
        nodeURL.setSchemeUse(true);
        FriendlyService friendlyService = getApplicationComponent(FriendlyService.class);
        String link = friendlyService.getFriendlyUri(nodeURL.toString());

        return link;
    }
*/
    public static class EditActionListener extends EventListener<UIAddOnSearchOne> {

        @Override
        public void execute(Event<UIAddOnSearchOne> event) throws Exception {

            UIAddOnSearchOne uiAddOnSearchOne = event.getSource();
            UIAddOnSearchPageLayout uiAddOnSearchPageLayout = uiAddOnSearchOne.getAncestorOfType(UIAddOnSearchPageLayout.class);
            UIPopupContainer uiPopupContainer = uiAddOnSearchPageLayout.getChild(UIPopupContainer.class);
            UIAddOnSearchEdit uiAddOnSearchEdit = uiPopupContainer.createUIComponent(UIAddOnSearchEdit.class, null, null);
            uiAddOnSearchEdit.setNodeId(uiAddOnSearchOne.getNodeId());
            uiAddOnSearchEdit.reset();
            uiPopupContainer.activate(uiAddOnSearchEdit, 600, 670);
            uiPopupContainer.setRendered(true);
            event.getRequestContext().addUIComponentToUpdateByAjax(uiPopupContainer);
        }

    }

    public static class DetailActionListener extends EventListener<UIAddOnSearchOne> {

        @Override
        public void execute(Event<UIAddOnSearchOne> event) throws Exception {

            UIAddOnSearchOne uiAddOnSearchOne = event.getSource();
            UIAddOnSearchPageLayout uiAddOnSearchPageLayout = uiAddOnSearchOne.getAncestorOfType(UIAddOnSearchPageLayout.class);
            UIAddOnSearchForm.filterSelected = "default";
            UIAddOnDetail uiAddOnDetail = uiAddOnSearchPageLayout.getChildById(UIAddOnSearchPageLayout.ADDON_DETAIL);
            uiAddOnDetail.setNodeId(uiAddOnSearchOne.getNodeId());
            uiAddOnSearchPageLayout.manageView(UIAddOnSearchPageLayout.ADDON_DETAIL);
            PortletRequestContext portletRequestContext = (PortletRequestContext) event.getRequestContext();
            portletRequestContext.addUIComponentToUpdateByAjax(uiAddOnSearchPageLayout);
        }

    }

    public static class InstallActionListener extends EventListener<UIAddOnSearchOne> {

        @Override
        public void execute(Event<UIAddOnSearchOne> event) throws Exception {

            UIAddOnSearchOne uiAddOnSearchOne = event.getSource();

            String addonIdentifier = uiAddOnSearchOne.getNodeId();
            try {
                String target = new String(ADDON_SCRIPT_LINUX+" "+INSTALL+" "+addonIdentifier);
              // String target = new String("mkdir stackOver");
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec(target);
                proc.waitFor();
                StringBuffer output = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line = "";
                while ((line = reader.readLine())!= null) {
                    output.append(line + "\n");
                }
               log.info(output);
            } catch (Exception e) {
                log.error("hello",e);
            }
        }
    }
    public static class UnInstallActionListener extends EventListener<UIAddOnSearchOne> {

        @Override
        public void execute(Event<UIAddOnSearchOne> event) throws Exception {

         /*   UIAddOnSearchOne uiAddOnSearchOne = event.getSource();

            String addonIdentifier = uiAddOnSearchOne.getStrProperty("exo:"+UIAddOnWizard.ADDON_INSTALL_COMMAND).replace("./addon", "").replace("install", "").replace(" ", "");

            ProcessBuilder pb = new ProcessBuilder(ADDON_SCRIPT_LINUX, INSTALL, addonIdentifier);

            Process p = null;
            try {
                p = pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }

    }

}
