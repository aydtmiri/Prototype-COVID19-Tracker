// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');
const GeoFire = require('geofirestore').GeoFirestore;
const {
  GeoCollectionReference,
  GeoFirestore,
  GeoQuery,
  GeoQuerySnapshot
} = require('geofirestore');
const FieldValue = require('firebase-admin').firestore.FieldValue;
// The Firebase Admin SDK to access Cloud Firestore.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// ------------------------------ GET COVID-19 CASES----------------------------------------------

exports.getCoronaCase = functions.https.onRequest((req, res) => {

      const notificationID = context.params.notificationID;
      const reporterID = snap.data().reporter;
      const user = snap.data().affectedUser;

      console.log("Deleted notification ID :",notificationID);
      console.log("Deleted notification ID :",snap.data());

      if(notificationID!= null){

        //------delete corona_case
        admin.firestore().collection('corona_case').doc(notificationID).delete();

        //------delete corona_location
        admin.firestore().collection('corona_location').doc(notificationID).delete();

        user.forEach(function(userID) {
          //------delete corona_notification field in private user
          admin.firestore().collection('private_user').doc(userID).update({'coronaNotification': admin.firestore.FieldValue.arrayRemove(notificationID)});
        });

        //------delete corona_notification field in private user
        admin.firestore().collection('specialist').doc(reporterID).update({'coronaNotification': admin.firestore.FieldValue.arrayRemove(notificationID)});


        console.log('Successfully deleted corona notification and related documents ans fields');

          return null;

      }
          console.log('The notification ID is null');

         return null;



  });




// ------------------------------ DELETE COVID-19 NOTIFICATION-------------------------------------
exports.deleteNotification = functions.firestore
  .document('corona_notification/{notificationID}')
  .onDelete((snap, context) => {

      const notificationID = context.params.notificationID;
      const reporterID = snap.data().reporter;
      const user = snap.data().affectedUser;

      console.log("Deleted notification ID :",notificationID);
      console.log("Deleted notification ID :",snap.data());

      if(notificationID!= null){

        //------delete corona_case
        admin.firestore().collection('corona_case').doc(notificationID).delete();

        //------delete corona_location
        admin.firestore().collection('corona_location').doc(notificationID).delete();

        user.forEach(function(userID) {
          //------delete corona_notification field in private user
          admin.firestore().collection('private_user').doc(userID).update({'coronaNotification': admin.firestore.FieldValue.arrayRemove(notificationID)});
        });

        //------delete corona_notification field in private user
        admin.firestore().collection('specialist').doc(reporterID).update({'coronaNotification': admin.firestore.FieldValue.arrayRemove(notificationID)});


        console.log('Successfully deleted corona notification and related documents ans fields');

          return null;

      }
          console.log('The notification ID is null');

         return null;



  });



// ------------------------------ SEND COVID-19 NOTIFICATION -------------------------------------
exports.sendNotificationNewCase = functions.firestore
  .document('corona_notification/{notificationID}')
  .onCreate((snap, context) => {

    const notificationID = context.params.notificationID;
    if (notificationID == null)
      return null;


    console.log('New covid-19 notification with ID: ', notificationID);
    // Get an object representing the document
    const newNotification = snap.data();
    // perform desired operations ...

    const payload = {
      notification: {
        title: 'Neue COVID-19 Meldung',
        body: 'Es gibt eine neue COVID-19 Meldung in Ihrer Nähe. Bitte öffnen Sie die App für mehr Informationen.'
      }

    };


    admin.firestore().collection('corona_location').doc(notificationID).get().then(location => {
      if (!location.exists) {
        console.log('There is no corona location with ID:', notificationID);
      } else {

        const object = admin.firestore();
        const geoFirestore = new GeoFire(object);
        const geoCollection = geoFirestore.collection('user_location');
        const geoQuery = geoCollection.near({
          center: new admin.firestore.GeoPoint(48.04, 8.19498),
          radius: newNotification.notificationRadius
        });

        // Where we will store the results
        const privateUserRef = admin.firestore().collection('private_user')

        // Get query (as Promise)
        geoQuery.get().then((value) => {
          // All GeoDocument returned by GeoQuery, like the GeoDocument added above
          var documents = value.docs

          documents.forEach(function(ids) {


            admin.firestore().collection('private_user').doc(ids.id).update({
              'coronaNotification': admin.firestore.FieldValue.arrayUnion(notificationID)
            });

            admin.firestore().collection('corona_notification').doc(notificationID).update({
              'affectedUser': admin.firestore.FieldValue.arrayUnion(ids.id)
            });

            admin.firestore().collection('private_user').doc(ids.id).get().then(user => {

              console.log(user._fieldsProto.token.stringValue);
              admin.messaging().sendToDevice(user._fieldsProto.token.stringValue, payload)
                .then((response) => {
                  console.log("Successfully sent message:", response);
                })
                .catch((error) => {
                  console.log("Error sending message:", error);
                });


            });

          });


          return console.log("Sent all notifications");
        });



      }

    });

    return null;

  });
