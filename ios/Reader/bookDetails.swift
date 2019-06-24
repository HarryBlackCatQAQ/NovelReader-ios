//
//  bookDetails.swift
//  Reader
//
//  Created by 李润泽 on 2019/6/24.
//  Copyright © 2019 李润泽. All rights reserved.
//

import UIKit

class bookDetails: UIViewController {
    @IBOutlet weak var backBtn: UIBarButtonItem!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let leftBarBtn = UIBarButtonItem(title: "返回", style: .plain, target: self,action: "backToPrevious")
        self.navigationItem.leftBarButtonItem = leftBarBtn
    }
    
    func backToPrevious(){
        self.navigationController?.popViewController(animated: true)
    }
}
