/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omnipop;

import java.util.Scanner;

/**
 *
 * @author PC-DEV
 */
public class OMNIPOP {
    
public static void main(String[] args) {
    
		// Variable pour le tableau cours
		String[] automne_18 = { "Base de donnees", "algorithme" };
		String[] hivers_19 = { "Oriente object", "base de donnees", "algorithme" };
		String[] ete_19 = { "Structure de donnees", "Oriente object" };
		String[][] cours = { automne_18, hivers_19, ete_19 };

		// Variable pour le tableau etudiant
		String[][] etudiants = new String[50][5];

		// Variable pour authentification et sortie du programme
		boolean estAuthentifie = login();
		String option;
		Scanner sc = new Scanner(System.in);
		
		//On stimule une session ici
		if (estAuthentifie) {
			do {
                //Appel de la methode affichage du menu principal
				afficherMenu();
				option = sc.next();
				switch (option) {
				case "1":
                    //Appel de la methode affichage  cours par session et prend en parametre cours
					afficherCoursePourSession(cours);
					break;
				case "2":
                    //Appel de la methode affichage qui liste tous les cours 
					listerToutLesCours(cours);
					break;
				case "3":
                    //Appel de la fonction ajouter un etudiant 
					ajouterEtudiant(etudiants);
					break;
				case "4":
                    //Appel de la methode  qui permet de rechercher un etudiant par nom ou prenom
					trouverEtudiant(etudiants);
					break;
                                
				}
			} while (!option.equals("5"));
			System.out.println("Vous avez choisi de quitter le programme.....");
		}else {
			System.err.println("Votre authentification a echoue...");
		}
	}
//Methode pour s'authentifier à l'application
	private static boolean login() {
        System.out.println("*******************AUTHENTIICATION********************");
	
		boolean estAuthentifie = false;
		Scanner sc = new Scanner(System.in);
		String nomUtilisateur = "";
		String motPasse = "";
		boolean credentielsOK = false;
		int nbessaie = 0;

		do {
			System.out.print("Entrer votre nom d'utilisateur : ");
			nomUtilisateur = sc.next();
			System.out.print("Entrer votre mot de passe : ");
			motPasse = sc.next();
			nbessaie++;
			credentielsOK = nomUtilisateur.equals("mlazar") && motPasse.equals("1234");
			if (!credentielsOK) {
				System.out.println("erreur,il vous reste  :" + (3 - nbessaie) + " essai(s)");
			} else {
                            estAuthentifie = true;
			}
			 
		} while (!credentielsOK && nbessaie < 3);
		return estAuthentifie;
	}
    
    
  //Methode affichage du menu 
	private static void afficherMenu() {
        System.out.println(" ");
        System.out.println("*******************MENU PRINCIPAL*********************");
		
		System.out.println(
				"1. Lister les vous cours pour une session. \n2." + " Lister tous vos cours. \n3. Ajouter un etudiant."
						+ "\n4. Afficher les info d'étudiant" + "\n5. Quitter le programme");
         System.out.println("Veuillez choisir une des option ci-dessus.");
	}

    
    //Methode ajoutant un etudiant a la table des etudiant
	private static void ajouterEtudiant(String[][] etudiants) {
		// Verifier si la taille du tableu est atteinte
		if (etudiants[49][0] != null) {
			System.out.println("Vous ne pouvez plus ajouter d'etudiants");
			return;
		}

		System.out.println("Ajouter etudiant");
		boolean ajouterEncore = true;
		String option;
		Scanner sc = new Scanner(System.in);
		String[] nouveauEtudiant;

		do {
			nouveauEtudiant = creerEtudiant();
			String id_nouveauEtudiant = nouveauEtudiant[0];
			// Si c'est le premier etudaint on l'ajoute directement. pas de control a faire.
			if (etudiants[0][0] == null) {
				etudiants[0] = nouveauEtudiant;

			} else {
				// Si non il faut faire les verifications
				for (int i = 0; i < etudiants.length; i++) {
					if (etudiants[i][0] != null && etudiants[i][0].equalsIgnoreCase(id_nouveauEtudiant)) {
						System.out.println("Cet ID est deja pris. L'insertion de ce etudinat a echouee");
						break;
					} 
	
					//Le premier index disponible
					if(etudiants[i][0] == null) {
						etudiants[i] = nouveauEtudiant;
						break;
					}
				}
			}
			
			//Affciher les etudiants apres chaque entree.
			afficherTousLesEtudiants(etudiants);
			
			//Donner la possibilite d'ajouter d'autres etudiants
			System.out.println("Voulez-vous ajouter un autre etudiant?(O/N)");
			option = sc.next();
			if (option.equalsIgnoreCase("N")) {
				ajouterEncore = false;
			}
		} while (ajouterEncore);

	}

	private static void imprimerTableau(String[] tableau) {
		for (int i = 0; i < tableau.length; i++) {
			System.out.print(tableau[i] + " | ");
		}
		System.out.println();

	}

	// Rechercher un etudiant a travers son nom et prenom
	public static void trouverEtudiant(String[][] etudiants) {
		
		// D'abord il faut un Scanner pour recupere le id qu'on cherche
		String nom, prenom;
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrer le nom de l'étudiant á rechercher");
		nom = sc.next();
		System.out.println("Entrer le prénom de l'étudiant á rechercher");
		prenom = sc.next();

		String [] etudiantRechercher = null;
        
		for (int i = 0; i < etudiants.length; i++) {
			if (		etudiants[i][0] != null 
					&& etudiants[i][1].equals(nom)
					&& etudiants[i][2].equals(prenom))
			{
				etudiantRechercher = etudiants[i];
                                //[[et1].[et2],[et3],......,[et50]]
				break;
			}
		}
		
		if(etudiantRechercher != null) {
			imprimerTableau(etudiantRechercher);
		}else {
			System.out.println("Ce etudiant n'existe pas \n");
		}
	}

	// Methode qui permet de créer un etudiant
	private static String[] creerEtudiant() {
	     
        String exp_nom_prenom = "^[A-Z]{1}[a-z]+$";
        String expAnneeNaissance = "^[0-9]{4}$";
        String expUserId = "^[0-9]{1,3}$";
        int n=3;
		Scanner sc = new Scanner(System.in);
        char rep;
		String user_id, nom, prenom, annee_naiss, userId;
		System.out.print("Entrer l'Id de l'étudiant  composé de 3 chiffres  :");
		user_id = sc.next();
        
 
            while(!user_id.matches(expUserId)){

			System.err.println("Invalide ID user,il doit etre composé de 3 chiffre au maximun :");
            user_id = sc.next();
            
            }

		System.out.print("Entrer le nom de l'étudiant avec la 1ère lettre en majuscule :");
		nom = sc.next();
        
		 
            while(!nom.matches(exp_nom_prenom)){
              
			System.err.println("Erreur ,la première lettre du nom  en majuscule SVP");
            nom = sc.next();
            }
      
            System.out.println("Entrer le  prenom de l'etudiant avec 1ère lettre en majuscule  ");
            prenom = sc.next();
        
            while(!prenom.matches(exp_nom_prenom)){
                
			System.err.println("Erreur ,la première lettre  du prenom doit etre en  majuscule SVP");
            prenom = sc.next();
            }
       
		
            System.out.println("Entrer l'année de naissance de l'étudiant   :");
            annee_naiss = sc.next();
         
           while(!annee_naiss.matches(expAnneeNaissance) && annee_naiss.length()!=4)
            {
                
			System.err.println("Invalide,année de naissance est composée de 4 chiffres :");
            annee_naiss = sc.next();
            }
		if (nom.length() > 3) {
			userId = nom.substring(0, 3) + prenom.substring(0, 1) + annee_naiss;
		} else {
			userId = nom + prenom.substring(0, 1) + annee_naiss;
		}

		return new String[] { user_id, nom, prenom, annee_naiss, userId };
	}
  //Methode qui permet de lister tous les cours 
	private static void listerToutLesCours(String[][] lesCours) {
		System.out.println("Liste de tous les cours:");
		for (int i = 0; i < lesCours.length; i++) {
            if(i==1)
            {
                System.out.print("AUTOMNE 2018 :");
                imprimerTableau(lesCours[i]);
            }
            if(i==2){
                System.out.print("HIVERS 2019  :");
                imprimerTableau(lesCours[i]);
            } 
            if(i==2){
            
                System.out.print("ETE 2019     :");
                imprimerTableau(lesCours[i]);
            
            }
		}
		System.out.println("\n");
        
	}
  //Methode listant les cours pour une session donnée.
	static void afficherCoursePourSession(String[][] lesCours) {
		Scanner sc = new Scanner(System.in);
		String session = "";
       char rep;
        do{
		System.out.println("Veuillez choisir preciser la session en choisissant: "
				+ "\n1. pour automne 2018\n2. pour hivers 2019\n3. pour ete 2019");
		session = sc.next();

		switch (session) {
		case "1":
            System.out.println("********SESSION AUTOMNE 2018***********");
			afficherCour(lesCours, 0);
			break;
		case "2":
             System.out.println("********SESSION HIVERS 2019***********");
			afficherCour(lesCours, 1);
			break;
		case "3":
             System.out.println("********SESSION ETE 2019***********");
			afficherCour(lesCours, 2);
			break;
		default:
			System.out.println("Votre choix n'est pas valable, veuillez relancer le programme");
            
	 
            }
            do{
                System.out.println("Voulez-vous  visualiser une autre session O/N ?");
                rep = sc.next().charAt(0);
            }while(rep !='O' && rep !='o' && rep !='N'&& rep !='n');
         }while (rep =='O' || rep =='o');
		
	}
//Methode listant tous les cours
	static void afficherCour(String[][] cours, int positionSession) {
		for (int i = 0; i < cours[positionSession].length; i++) {
			System.out.print(cours[positionSession][i] + " |");
		}
		System.out.println("\n");
	}
	//Methode affichant tous les etudiants
	private static void afficherTousLesEtudiants(String [][] etudiants){
		System.out.println("La liste  des etudiants actuels:");
		for(int i = 0; i< etudiants.length; i++) {
				if(etudiants[i][0] != null) {
					imprimerTableau(etudiants[i]);
				}
		}
	}
}