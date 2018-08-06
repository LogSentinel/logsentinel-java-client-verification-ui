package com.logsentinel.verificationui.web;

import com.logsentinel.verificationui.model.TreeMap;
import com.logsentinel.verificationui.util.VisualizationUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapController {

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public TreeMap getTreeMap(@RequestParam("treeSize") int treeSize) {
        return VisualizationUtil.generateMap(treeSize);
    }

}
