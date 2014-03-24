Java-Updater
============

That's a simple java updater.
Host your files on the server, and the app load version key/path, and download/update the used folder. (Where is the application)

# How to configure and use ?

Configure client and ftp configs. (releases must be at .zip extension)

####Exemple:

 * Client config.conf (it's where is the updater)



        connection {
            url {
                config = "http://updater.com/updater"
            }
        }


Here, the program will find the distant config.conf at folder updater of your ftp (in this exemple)


 * Your distant config.conf (on your ftp server):


        release {

            folder = releases
            required_lasts = 0.1,0.2
            current = 0.3


            files {
                required = "dofus.exe"
            }
        }

Then, the program will update/create files of directory witch contains updater, if it's necessary.


 * folder is the folder which contains releases at .zip extension
 * required_lasts contains necessary releases before the last release
 * current is the last release
 * files.required is the file which is util to know if the updater is in the good folder (imagine if the client must put himself the updater in the program folder)

Last exemple, if you have understand, you can have a release here: http://updater.com/updater/releases/0.1.zip


# Required file

 * In the client config.conf, you must specify what program is obligatory.
 * It's simple to know if the updater is in the good folder.

# Security Exception

 * 1) Create your jar file like try2.jar containing some mainifest file-- My1.mf as like always.So, the absolute path of the jar file would be C:\try.jar.
 * 2) Now you need to download a software "Launch4j" which will help you to wrap jar files.Its download links is : http://sourceforge.net/projects/launch4j/files/launch4j-3/3.1.0-beta2/
 * 3) Now take out 5 min. and watch this tutorial: http://www.youtube.com/watch?v=mARUFRknTYQ ;this will tell you basic functions of Launch4j. But here it won't be clear how to create a manifest file for your exe.
 * 4) After learning this, then create a manifest file, it is a simple text file with extension ".manifest" saved. But here certain things to be taken care of: Firstly, your mainfest file has to have same name as that of your final exe to be created. In my case, name of my exe was supposed to be "Launchme.exe" thus, my manifest file had to be named as "Launchme.manifest". Secondly, in manifest file just copy this content:


  `<?xml version="1.0" encoding="UTF-8" standalone="yes"?> `

 ` <assembly xmlns="urn:schemas-microsoft-com:asm.v1" manifestVersion="1.0"> `

 ` <trustInfo xmlns="urn:schemas-microsoft-com:asm.v3">`

  `<security>`

 ` <requestedPrivileges>`

 ` <requestedExecutionLevel level="highestAvailable"   uiAccess="False" />`

 ` </requestedPrivileges>`

 ` </security>`

 ` </trustInfo>`

  `</assembly>`


copy the above code into your manifest file. here line 6 is the key of whole issue. Then save it and close it.

 * 4)Now start Launch4j, fill all the taught textfields as like in video as per your conditions. In Wrapper mainfest column add this file manifest file. Then click "save configuration" option and then click-- Build Wrapper.
 * Now you have exe containing your jar which requests user to give admin rights before executing. Now user is free from knowing anything other than clicks!