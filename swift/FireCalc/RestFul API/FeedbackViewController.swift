//
//  SigninViewController.swift
//  RestFul API
//
//  Created by c Mahapatabendige on 3/5/2018.
//

import UIKit

class FeedbackViewController: UIViewController {
    
    var menuCreated = false
    
    @IBOutlet weak var textTitle: UITextField!
    @IBOutlet weak var txtDescription: UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    @IBAction func btnAddFeedback(_ sender: UIButton) {
        let defaults = UserDefaults.standard
        let params = ["title":textTitle.text,
                      "description":txtDescription.text,
                      "userid": defaults.object(forKey: "username")]
        
        if textTitle.text != "" && txtDescription.text != ""{
            
            IJProgressView.shared.showProgressView(view)
            
            do {
                
                let opt = try HTTP.POST(Constants.FeedbackApi, parameters: params,headers: nil)
                
                opt.start { response in
                    
                    if response.error == nil {
                        
                        IJProgressView.shared.hideProgressView()
                        
                    }
                    
                }
            } catch let error {
                print("got an error creating the request: \(error)")
            }
            
        }
        
        self.textTitle.text  = ""
        self.txtDescription.text = ""
        
        IJProgressView.shared.hideProgressView()
        
        showToast(message: "Feedback successfully added")
        
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


