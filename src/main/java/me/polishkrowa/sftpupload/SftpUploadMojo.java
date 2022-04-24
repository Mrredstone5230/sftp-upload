package me.polishkrowa.sftpupload;

import com.jcraft.jsch.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "sftpupload", defaultPhase = LifecyclePhase.PACKAGE)
public class SftpUploadMojo extends AbstractMojo {

    @Parameter(property = "source")
    String source;

    @Parameter(property = "to", required = true)
    String to;

    @Parameter(property = "host", required = true)
    String host;

    @Parameter(property = "user", required = true)
    String user;

    @Parameter(property = "password")
    String password;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    //get user's base dir with maven
    @Parameter(property = "userhome", defaultValue = "${user.home}")
    String userhome;



    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (source == null) {
            source = project.getArtifact().getFile().getAbsolutePath();
        }

        try {
            ChannelSftp channelSftp = setupJsch();
            channelSftp.connect();
            //upload source to to
            channelSftp.put(project.getArtifact().getFile().getAbsolutePath(), to);
            channelSftp.chmod(504, to);
            channelSftp.chgrp(1004, to);
            channelSftp.exit();
            getLog().info("Uploaded " + source + " to " + to);

        } catch (JSchException | SftpException e) {
            getLog().error("Error while uploading " + source + " to " + to);
            e.printStackTrace();
        }
    }

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts(userhome + "/.ssh/known_hosts");
        Session jschSession = jsch.getSession(user, host);
        jschSession.setPassword(password);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }
}