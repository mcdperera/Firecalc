//
//  SignupViewController.swift
//  FireCalc
//
//  Created by MAHAPATABENDIGE CHARMAL D on 3/6/18.
//  Copyright © 2018 tpcreative.co. All rights reserved.
//
import UIKit

class RegisterViewController: UIViewController {
    
    @IBOutlet weak var textFieldPassword: UITextField!
    @IBOutlet weak var textFieldEmail: UITextField!
    @IBOutlet weak var textFileldName: UITextField!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        
    }

    @IBAction func btnRegister(_ sender: UIButton) {
        
        let params = ["name":  textFileldName.text,
                      "email": textFieldEmail.text,
                      "isAdmin" : "false" as String?,
                      "password": textFieldPassword.text]
        
        if textFileldName.text != "" && textFieldEmail.text != "" && textFieldPassword.text != ""{
            
            IJProgressView.shared.showProgressView(view)
            
            do {
                
                let opt = try HTTP.POST(Constants.SignUpApi, parameters: params,headers: nil)
                
                opt.start { response in
                    
                    if response.error == nil {
                        
                        IJProgressView.shared.hideProgressView()
                        
                    }
                    
                }
            } catch let error {
                print("got an error creating the request: \(error)")
            }
            
        }
        
        self.textFileldName.text  = ""
        self.textFieldEmail.text = ""
        self.textFieldPassword.text = ""
        
        IJProgressView.shared.hideProgressView()
        
        showToast(message: "User signup successfully")
        
        let viewController:ViewController = ViewController()
        
        self.present(viewController, animated: true, completion: nil)
        
    }
    
    func showToast(message : String) {
        
        let toastLabel = UILabel(frame: CGRect(x: self.view.frame.size.width/2 - 75, y: self.view.frame.size.height-100, width: 250, height: 35))
        toastLabel.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        toastLabel.textColor = UIColor.white
        toastLabel.textAlignment = .center;
        toastLabel.font = UIFont(name: "Montserrat-Light", size: 12.0)
        toastLabel.text = message
        toastLabel.alpha = 1.0
        toastLabel.layer.cornerRadius = 10;
        toastLabel.clipsToBounds  =  true
        self.view.addSubview(toastLabel)
        UIView.animate(withDuration: 4.0, delay: 0.1, options: .curveEaseOut, animations: {
            toastLabel.alpha = 0.0
        }, completion: {(isCompleted) in
            toastLabel.removeFromSuperview()
        })
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
}


