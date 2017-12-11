package br.com.welisson.atm;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;

public class SqlExecuter extends SQLExec {
    public SqlExecuter() {
        Project project = new Project();
        project.init();
        setProject(project);
        setTaskType("sql");
        setTaskName("sql");
    }
}
