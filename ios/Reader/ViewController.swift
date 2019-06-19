//
//  ViewController.swift
//  Reader
//
//  Created by 李润泽 on 2019/6/13.
//  Copyright © 2019 李润泽. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

struct GroceryProduct: Codable{
    var name: String
    var points: Int
    var description: String
}

class ViewController: UIViewController{
    @IBOutlet weak var label: UILabel!
    var eHttp:HTTPController = HTTPController()
    
    override func viewDidLoad() {
        super.viewDidLoad()
//        var data:String = ""
        //eHttp.delegate = self
//        data = eHttp.onSearch(url: "http://localhost:8080/test")
        //print("idx: " + data)
        //label.text = "data : \(data)"
        
        GetMes();
        
        
        
    }
    
    func GetMes(){
        var headers:Dictionary = [String:String]()

shi        Alamofire.request("http://localhost:8080/searching", method: .get,  parameters: ["content":"斗罗大陆"]).responseJSON{ (data) in
            if data.result.isSuccess {
                //self.label.text = (self.didRecieveResults(results: data.result.value as AnyObject))
                print("===" + (self.didRecieveResults(results: data.result.value as AnyObject)))
            }else{
                print("DATA获取失败")
            }
        }
        
//        Alamofire.request("http://localhost:8080/test",method: .get, parameters: "斗罗大陆").responseJSON{ (data) in
//            if data.result.isSuccess {
//                self.label.text = (self.didRecieveResults(results: data.result.value as AnyObject))
//                //                print("===" + res)
//            }else{
//                print("DATA获取失败")
//            }
//        }
    }
    
    func didRecieveResults(results: AnyObject) -> String{
        let json = JSON(results)
       print(json)
        let idx = json[0]["idx"].stringValue
        print(idx)
        return idx;
    }
    
}

