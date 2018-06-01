<?php

// API access key from Google API's Console
define( 'API_ACCESS_KEY', 'AAAA2Eo3WLI:APA91bGWu5-YnMINfQ2dMJvGm7CDBBQgzUQAjguZfFkaESPrL6v5r5Fws9i0cMZ64QtupR_9PjRKtXIWlC4sQDkeCeoU69oYtD2exFMtmCnEhomKwunbHJBM2uFWh9NZHZHhb8QFyHG_' );

// App key
$registrationIds = array(
	"fmzv-YtTWJU:APA91bFQ9-pglODSDqi6-Cwf-tnGt0xHAvd1ZLwLvl_kvOVgeQBPfz3raR7KObxoxFQr9C7Fbe60h7o_ykwHelGgVHEzvzfR2jK0ywXj1Rs7jZZ9uaDpaRRKP9gHcQZPijl8Agc5a386"
);

// prep the bundle
$msg = array
(
    'title'         => 'This is a title',
    'message'       => 'Here is a message'
);

$fields = array
(
  'registration_ids'  => $registrationIds,
  'data'              => $msg,
);

$headers = array
(
    'Authorization: key=' . API_ACCESS_KEY,
    'Content-Type: application/json'
);

$ch = curl_init();
curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
curl_setopt( $ch,CURLOPT_POST, true );
curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
$result = curl_exec($ch );
curl_close( $ch );

echo $result;

?>