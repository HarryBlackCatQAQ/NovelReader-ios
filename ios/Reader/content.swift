//
//  content.swift
//  Reader
//
//  Created by 李润泽 on 2019/6/25.
//  Copyright © 2019 李润泽. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class content: UIViewController {
    @IBOutlet weak var text: UITextView!
    
    var message:NSMutableDictionary = NSMutableDictionary()

    override func viewDidLoad() {
        let headers: HTTPHeaders = [
            "Content-Type": "application/json"
        ]
        Alamofire.request("http://localhost:8080/readContent", method: .post,  parameters: message as! [String:AnyObject], encoding: JSONEncoding(options: []) ,headers:headers).responseJSON{ (data) in
            if data.result.isSuccess {
                var jsonData:JSON
                jsonData = (self.didRecieveResults(results: data.result.value as AnyObject))
                self.text.text = jsonData["content"].stringValue                
                self.navigationItem.title = jsonData["title"].stringValue
                
            }else{
                print("DATA获取失败")
            }
        }
    }
    
    func didRecieveResults(results: AnyObject) -> JSON{
        let json = JSON(results)
        return json
    }
}
