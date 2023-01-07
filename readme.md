# sftpupload - maven plugin
***I have no idea about what I'm doing.***

### **TESTED AND MADE ON MACOS. HAS SOME NON-WINDOWS FRIENDLY CODE (At least I think)**

this maven plugin allows to upload build artefact to a remove sftp server.

to use plugin on your project, you need to add the following repo to your pom.xml:

```xml
<pluginRepositories>
    <pluginRepository>
        <id>redserv-repo</id>
        <url>https://repo.redserv.net/repository/maven-public/</url>
    </pluginRepository>
</pluginRepositories>
```
and the following plugin to your build->plugins tag :  (Don't forget to replace the info in the  {} with your own)
```xml
<plugin>
    <groupId>me.polishkrowa</groupId>
    <artifactId>sftpupload</artifactId>
    <version>1.2</version>

    <executions>
        <execution>
            <id>upload-to-server</id>
            <phase>package</phase>
            <goals>
                <goal>sftpupload</goal>
            </goals>
            <configuration>
                <to>{path to remote}</to>
                <host>{sftp.host-address}</host>
                <user>{sftp.username}</user>
                <password>{sftp.password}</password>
            </configuration>
        </execution>
    </executions>
</plugin>
```

I also recommend the usage of the `properties-maven-plugin` plugin to add login info in an external config file (that you would .gitignore)

Have fun!!

