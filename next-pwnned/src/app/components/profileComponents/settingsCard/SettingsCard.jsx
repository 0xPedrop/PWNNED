import React from 'react';
import Card from '@/ui/components/Card';
import styles from './SettingsCard.module.css'; 

export default function SettingsCardContent() {
  return (
    <div>
      <Card 
          width="600px" 
          height="190px" 
          backgroundColor="#F5F5F5" 
          color="#000000" 
          shadow=".4rem .4rem 0 #000000" 
          border="3px solid #000000"
          className={styles.plainSettings}
          >
            <h1 className={styles.title}>Plain Settings</h1>
            <div className={styles.plain}>
              <h1><b>Actual Plain</b></h1>
              <h1><i>Free</i></h1>
            </div>
            <a href="" className={styles.upgradeButton}>Upgrade to Premium</a>
      </Card>
      <Card 
        width="600px" 
        height="190px" 
        backgroundColor="#F5F5F5" 
        color="#000000" 
        shadow=".4rem .4rem 0 #000000" 
        border="3px solid #000000"
        >
        <h1><b>Language Settings</b></h1>
      </Card>
    </div>
  );
}