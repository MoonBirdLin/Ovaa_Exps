# some of the exploits of Oversecured OVAA Application

- Reference : https://github.com/cyb3r-w0lf/Exploits-Ovaa
- Contained Vuls : 
  - Access to arbitrary activities and acquiring access to arbitrary content providers in `LoginActivity` by supplying an arbitrary Intent object to `redirect_intent`.
  - Theft of arbitrary files in `MainActivity` by intercepting an activity launch from `Intent.ACTION_PICK` and passing the URI to any file as data.
  - Insecure broadcast to `MainActivity` containing credentials. The attacker can register a broadcast receiver with action `oversecured.ovaa.action.UNPROTECTED_CREDENTIALS_DATA` and obtain the user's data.
  - Use of very wide file sharing declaration for `oversecured.ovaa.fileprovider` content provider in `root` entry.

- Vulnerabilities Analysis : [OVAA_analysis](./OVAA_Vul_Analysis.pdf)