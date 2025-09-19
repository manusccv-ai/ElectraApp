# ElectraApp

تطبيق Android Native لإدارة المشتركين والفواتير، مبني باستخدام Kotlin و Jetpack Compose وقاعدة بيانات Room.

## الوظائف:

1.  **إدارة المشتركين:**
    *   إضافة مشترك جديد (الاسم، رقم الهوية، عدد الأمبيرات).
    *   تعديل أو حذف مشترك.
    *   عرض قائمة بجميع المشتركين.

2.  **الفواتير والدفع:**
    *   إدخال سعر الأمبير الشهري (قيمة واحدة في الإعدادات).
    *   حساب تلقائي للمبلغ المستحق لكل مشترك (عدد الأمبيرات × سعر الأمبير).
    *   خيار لتحديد حالة الدفع (مدفوع ✅ أو غير مدفوع ❌).

3.  **التخزين:**
    *   استخدام Room (SQLite) لتخزين جميع البيانات محليًا.
    *   البيانات تبقى محفوظة حتى بعد إغلاق التطبيق.

## المتطلبات التقنية:

*   **اللغة:** Kotlin
*   **قاعدة البيانات:** Room (SQLite)
*   **واجهة المستخدم:** Jetpack Compose
*   **هيكل المشروع:** MVVM

## كيفية البناء والتشغيل:

1.  **فتح المشروع في Android Studio:**
    *   افتح Android Studio.
    *   اختر `File > Open`.
    *   انتقل إلى مجلد `ElectraApp` الذي تم فك ضغطه وحدده.
    *   انتظر حتى يقوم Gradle بمزامنة المشروع وتنزيل جميع التبعيات.

2.  **تشغيل التطبيق:**
    *   تأكد من توصيل جهاز Android فعلي أو تشغيل محاكي Android.
    *   انقر على زر `Run 'app'` (الأيقونة الخضراء المثلثة) في شريط الأدوات العلوي في Android Studio.
    *   سيتم بناء التطبيق وتثبيته وتشغيله على الجهاز/المحاكي المحدد.

3.  **البناء باستخدام Codemagic (أو أي CI/CD آخر):**
    *   المشروع جاهز للبناء على Codemagic. ستحتاج إلى تكوين ملف `codemagic.yaml` الخاص بك في جذر المشروع.
    *   مثال على إعدادات `codemagic.yaml` الأساسية:

    ```yaml
    workflows:
      android-build:
        name: Android Build
        environment:
          vars:
            JAVA_VERSION: 17
        trigger:
          branch:
            - master
            - main
        android:
          signing:
            - android_signing
        scripts:
          - name: Set up Java
            script: | 
              sdk install java $JAVA_VERSION
              sdk use java $JAVA_VERSION
          - name: Build Android Release
            script: | 
              ./gradlew assembleRelease
        artifacts:
          - app/build/outputs/**/*.apk
          - app/build/outputs/**/*.aab
    ```

    *   تأكد من إعداد توقيع التطبيق (App Signing) في Codemagic إذا كنت تقوم ببناء إصدارات Release.

## ملاحظات:

*   تم استخدام `kotlin-kapt` لمعالج التعليقات التوضيحية لـ Room. إذا واجهت مشاكل، يمكنك التبديل إلى `ksp` (Kotlin Symbol Processing) عن طريق تعديل `app/build.gradle.kts`.
*   تم تصميم واجهة المستخدم باستخدام Jetpack Compose، مما يوفر تجربة تطوير حديثة وسريعة.


