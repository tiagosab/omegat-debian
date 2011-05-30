﻿Ce document a été traduit par Didier Briel et Jean-Christophe Helary, copyright© 2008.

==============================================================================
  OmegaT 1.8.0 Fichier « Lisez-moi »

  1.  Informations à propos d'OmegaT
  2.  Qu'est ce qu'OmegaT ?
  3.  Installer OmegaT
  4.  Contribuer à OmegaT
  5.  Est-ce qu'OmegaT vous pose problème ? Avez-vous besoin d'aide ?
  6.  Détails de la version

==============================================================================
  1.  Informations à propos d'OmegaT


Les informations les plus récentes au sujet d'OmegaT sont à :
      http://www.omegat.org/

Aide utilisateur sur le groupe Yahoo. Vous pouvez y consulter les archives sans vous inscrire au groupe :
     http://groups.yahoo.com/group/OmegaT/

Demandes d'améliorations (en anglais), sur le site de SourceForge :
     http://sourceforge.net/tracker/?group_id=68187&atid=520350

Rapports de bogues (en anglais), sur le site de SourceForge :
     http://sourceforge.net/tracker/?group_id=68187&atid=520347

==============================================================================
  2.  Qu'est ce qu'OmegaT ?

OmegaT est un outil de Traduction assistée par ordinateur (TAO). OmegaT est distribué gratuitement, il n'est donc pas nécessaire de payer de licence d'utilisation même pour un usage professionnel. OmegaT est une application libre, vous avez donc le droit de la modifier et/ou de la redistribuer tant que vous respectez les termes de la licence utilisateur.

Les principales fonctions d'OmegaT sont :
  - capacité à fonctionner sur n'importe quel système d'exploitation compatible avec Java
  - utilisation de n'importe quel type de fichier TMX conforme comme mémoire de traduction de référence
  - système de segmentation de texte flexible (basé sur la norme SRX)
  - recherche de termes dans le projet et dans les mémoires de référence
  - recherche de fichiers dans les formats acceptés dans n'importe quel dossier 
  - correspondances automatiques
  - gestion intelligente des projets incluant des structures complexes de dossiers
  - glossaires (vérifications terminologiques)
  - documentation et tutoriel clairs et détaillés
  - localisation dans de nombreuses langues

OmegaT prend en charge directement les formats de fichier suivants :
  - texte pur
  - HTML et XHTML
  HTML Help Compiler
  - OpenDocument/OpenOffice.org
  - ensemble de ressources Java (Bundle.properties)
  - fichiers INI (fichiers dans un encodage quelconque constitués de paires clé=valeur)
  - fichiers PO monolingues (après passage par msgcat)
  - fichiers de documentation format DocBook
  - Fichiers Microsoft Open XML
  - Fichiers XLIFF Okapi monolingues

Il est également possible de personnaliser OmegaT pour accepter d'autres formats.

OmegaT est capable d'analyser les structures de dossiers les plus complexes pour y retrouver tous les fichiers lisibles. OmegaT recréera la même structure de dossiers pour vos documents traduits et y inclura une copie de tous les fichiers qu'il ne peut pas lire.

Pour commencer à utiliser OmegaT tout de suite, lancez OmegaT et lisez le tutoriel qui s'affiche dans la fenêtre principale : « OmegaT : Tutoriel premiers pas ».

Le manuel utilisateur se trouve dans le paquet que vous avez téléchargé, vous y avez accès à partir du menu [Aide] après avoir lancé OmegaT.

==============================================================================
 3. Installer OmegaT

3.1 Informations générales
Pour fonctionner, OmegaT a besoin d'un environnement d'exécution Java (JRE) de version 1.4 ou supérieure. Le JRE est maintenant fourni en standard avec OmegaT, afin d'éviter aux utilisateurs d'avoir à le sélectionner, l'obtenir et l'installer. 

Utilisateurs de Windows et Linux : si vous êtes certain qu'une version convenable du JRE est déjà installée, vous pouvez installer une version d'OmegaT sans le JRE (cela est indiqué par le nom de la version, « Without_JRE »). 
Si vous avez un doute quelconque, nous vous recommandons d'utiliser la version « standard », c'est à dire avec un JRE. Cela est sûr, puisque même si le JRE est déjà installé, cette version n'interférera pas avec la version système.

Utilisateurs de Linux : notez qu'OmegaT ne fonctionne pas avec les implémentations libres de Java incluses dans de nombreuses distributions. Celles-ci sont obsolètes ou incomplètes. Téléchargez un JRE de Sun à partir du lien ci-dessus, ou téléchargez et installez OmegaT fourni avec son JRE (le paquet .tar.gz marqué « Linux »).

Utilisateurs de Mac : le JRE est déjà installé sur Mac OX X.

Linux sur des architectures Power PC :  les utilisateurs devront télécharger le JRE d'IBM, puisque Sun ne fournit pas de JRE pour les systèmes PPC. Dans ce cas, téléchargez à partir de :
    http://www-128.ibm.com/developerworks/java/jdk/linux/download.html 

3.2 Installation
* Utilisateurs de Windows : Exécutez simplement le programme d'installation. Si vous le souhaitez, le programme d'installation peut créer des raccourcis pour exécuter OmegaT.
* Autres : Pour installer OmegaT, créez simplement un dossier approprié pour OmegaT
(ex. : /usr/local/lib on Linux). Copiez l'archive zip ou tar.gz d'OmegaT dans ce dossier, et décompactez-la.

3.3 Exécuter OmegaT
OmegaT peut être exécuté de différentes façons.

* Utilisateurs de Windows : en double-cliquant sur le fichier OmegaT.exe. Si vous pouvez voir le fichier OmegaT mais pas le fichier OmegaT.exe dans votre gestionnaire de fichier (Explorateur Windows), modifiez la configuration afin que les extensions soient affichées.

* En double-cliquant sur le fichier OmegaT.jar. Cela ne fonctionnera que le type de fichier .jar est associé à Java dans votre système.

* À partir de la ligne de commande. La commande pour exécuter OmegaT est :

cd <dossier dans lequel le fichier OmegaT.jar est situé>

<nom et chemin du fichier exécutable Java> -jar OmegaT.jar

(Le fichier exécutable Java est le fichier java sous Linux et java.exe sous Windows.
Si Java est installé au niveau système, il n'est pas nécessaire d'entrer le chemin complet.)

* Utilisateurs de Windows : Le programme d'installation peut créer des raccourcis pour vous dans le menu démarrer, sur le bureau ou dans la zone de lancement rapide. Vous également pouvez faire glisser le fichier OmegaT.exe dans le menu démarrer, sur le bureau ou dans la zone de lancement rapide afin d'y créer un raccourci.

* Utilisateurs de KDE Linux : vous pouvez ajouter OmegaT dans vos menus comme suit :

Centre de configuration - Bureau - Tableau de bord - Menus - Éditer le menu K - Fichier - Nouvel élément/Nouveau sous-menu.

Ensuite, après avoir sélectionné un menu approprié, ajoutez un sous-menu/élément avec Nouveau sous-menu et Fichier - Nouvel élément. Entrez OmegaT pour le nom du nouvel élément.

Dans le champ « Commande », utilisez le bouton de navigation pour trouver votre script d'exécution d'OmegaT, et sélectionnez-le. 

Cliquez sur le bouton icône (à droite des champs Nom/Description/Commentaire) 
- Autres icônes - Parcourir, et naviguez jusqu'au sous-dossier /images du dossier de l'application OmegaT. Sélectionnez l'icône OmegaT.png.

Enregistrez enfin les modifications avec Fichier - Enregistrer.

* Utilisateurs de KDE Gnome : vous pouvez ajouter OmegaT dans vos menus comme suit :

Effectuez un clic droit dans le tableau de bord - Créer un lanceur. Entrez « OmegaT » dans le champ « Nom » ; dans le champ « Commande », utilisez le bouton « Parcourir... » pour localiser votre script de lancement d'OmegaT. Sélectionnez-le et confirmez avec « Valider ».

==============================================================================
 4. S'impliquer dans le projet OmegaT

Pour participer au développement d'OmegaT, prenez contact avec les développeurs à :
    http://lists.sourceforge.net/lists/listinfo/omegat-development

Pour traduire l'interface utilisateur, le manuel ou d'autres documents, lisez :
      
      http://www.omegat.org/en/translation-info.html

Et inscrivez vous à la liste des traducteurs (multilingue) :
      http://lists.sourceforge.net/mailman/listinfo/omegat-l10n

Pour d'autres types de contributions, inscrivez vous d'abord au groupe des utilisateurs :
      http://tech.groups.yahoo.com/group/omegat/

Et voyez comment se passent les choses dans le monde d'OmegaT...

  OmegaT est l'œuvre de Keith Godfrey.
  Le coordinateur du projet OmegaT est Marc Prior.

Les personnes qui ont contribué incluent :
(ordre alphabétique)

Contributions au code :
  Zoltan Bartko
  Didier Briel (responsable de version)
  Kim Bruning
  Alex Buloichik
  Sandra Jean Chua
  Martin Fleurke  
  Thomas Huriaux
  Fabián Mandelbaum
  Maxym Mykhalchuk 
  Henry Pijffers 
  Tiago Saboga
  Andrzej Sawuła
  Benjamin Siband
  Martin Wunderlich

Autres contributions par :
  Sabine Cretella
  Dmitri Gabinski
  Jean-Christophe Helary
  Vito Smolej (responsable documentation)
  Samuel Murray
  Marc Prior (responsable localisations)
  ainsi que beaucoup d'autres personnes.

(Si vous pensez avoir contribué à OmegaT de manière significative et si votre nom ne se trouve pas sur cette liste, n'hésitez pas à nous contacter.)

OmegaT utilise les bibliothèques suivantes :

  HTMLParser par Somik Raha, Derrick Oswald, etc. (Licence LGPL)
  http://sourceforge.net/projects/htmlparser

  MRJ Adapter par Steve Roy (licence LGPL)
  http://homepage.mac.com/sroy/mrjadapter/

  VLDocking Framework 2.0 par VLSolutions (licence CeCILL)
  http://www.vlsolutions.com/fr/products/docking/

  Hunspell 1.1.12 par László Németh et d'autres personnes (Licence LGPL)

  JNA par Todd Fast, Timothy Wall et d'autres personnes (Licence LGPL)

  Swing-Layout 1.0.2 (licence LGPL)

  Backport-util-concurrent (domaine public)

  Retroweaver 2.0.1 (licence Retroweaver)

  Jmyspell 2.1.4 (licence LGPL)

==============================================================================
 5.  Est-ce qu'OmegaT vous pose problème ? Avez-vous besoin d'aide ?

Avant de faire un rapport de bogue, prenez la précaution de bien lire le manuel utilisateur. Ce que vous considérez comme un problème peut en fait être une fonction d'OmegaT. Si les mots suivants se trouvent dans le journal : « Erreur », « Avertissement », ou bien « a été avortée », vous avez probablement trouvé un problème. Le journal est le fichier log.txt situé dans le répertoire des préférences utilisateur (consulter le manuel pour déterminer son l'emplacement).

L'étape suivante est d'avoir votre découverte confirmée par d'autres utilisateurs pour s'assurer qu'un rapport n'a pas déjà été rempli. Vous pouvez aussi consulter la page des bogues sur SourceForge. Une fois que vous êtes sûr d'avoir découvert une séquence reproductible d'événements qui aboutit à un résultat non souhaitable, il est possible de remplir un rapport de bogue.

Un bon rapport de bogue comporte exactement 3 parties.
  - la séquence à reproduire,
  - le résultat que vous attendiez et
  - ce que vous avez vu à la place.

Il est possible d'ajouter des copies de fichiers, des portions du journal, des copies d'écran à votre rapport, tout ce qui vous semble qui aidera les développeurs à résoudre votre problème.

Les archives du groupe utilisateur sont à :
     http://groups.yahoo.com/group/OmegaT/

Pour consulter la page des rapports de bogue et pour remplir un nouveau rapport rendez-vous à :
     http://sourceforge.net/tracker/?group_id=68187&atid=520347

Il sera nécessaire de procéder à un enregistrement en tant qu'utilisateur de SourceForge si vous souhaitez recevoir des informations sur les suites données à votre rapport.

==============================================================================
6.   Détails de la version

Consultez « changes.txt » pour avoir des informations détaillées sur les modifications incluses dans cette version ainsi que les précédentes.


==============================================================================