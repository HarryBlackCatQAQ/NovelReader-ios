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

class ViewController: UIViewController,HttpProtocol {
    @IBOutlet weak var label: UILabel!
    var eHttp:HTTPController = HTTPController()
    override func viewDidLoad() {
        super.viewDidLoad()
        eHttp.delegate = self
        let obj = aaa();
        let a = obj.test();
        
        label.text = "\(a)"
        didRecieveResults(results: eHttp.onSearch(url: "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.billboard.billList&format=json&type=1&size=10") as AnyObject)
    }
    
    func didRecieveResults(results: AnyObject) {
        let json = JSON(results)
        print(json)
    }
    
}

