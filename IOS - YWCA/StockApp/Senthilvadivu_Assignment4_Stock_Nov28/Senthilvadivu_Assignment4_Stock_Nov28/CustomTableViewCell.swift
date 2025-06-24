//
//  CustomTableViewCell.swift
//  Senthilvadivu_Assignment4_Stock_Nov28
//
//  Created by user266706 on 11/29/24.
//

import UIKit

class CustomTableViewCell: UITableViewCell {
    
    
    // Create IBOutlets for the label and slider
    @IBOutlet weak var nameLabel: UILabel!
    
    
    @IBOutlet weak var priceLabel: UILabel!
    
    
    @IBOutlet weak var rankLabel: UILabel!
    
    
    @IBOutlet weak var rankSlider: UISlider!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    //let priceLabel = cell.viewWithTag(1) as! UILabel
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
