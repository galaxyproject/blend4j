package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.toolshed.beans.Tool;
import java.util.List;

public interface RepositoriesClient {

  List<Tool> getTools();
  
  Tool showTool(final String toolId);
  
}
