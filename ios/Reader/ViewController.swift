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
        didRecieveResults(results: eHttp.onSearch(url: "http://127.0.0.1") as AnyObject)
    }
    
    func didRecieveResults(results: AnyObject) {
        let json = JSON(results)
        print(json)
    }
    
}

