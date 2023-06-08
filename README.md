 MVVM Delivery Clone App🛒
**앱 개발 목차 및 설명


- gradle 설정 , 버전 맞추기 
- 패키지 만들기(data , di , screen , model , util ,widget) 
- screen패키지 안에 각 화면들 패키지 생성 
- application 생성  
- BaseActivity / BaseFragment / BaseViewModel 설계 
- widget에 기본 adapter 생성 후 model패키지에서 id / type을 이용해 diffutil 설계 
- util에 provider 패키지 생성 , ResourceProvider 생성 
- util에 ModelViewHolderMapper 생성해서 Model에서 type을 넘겨받아 viewholder객체를 반환하게 함. 
- EmptyViewHolder 를 생성해 Empty_CELL을 처리   
- data패키지에 필요한 요소들 생성 (entity / db / network / response / uri / repository) 
   
- provideAPI 에서 API통신을 위한 함수 생성
- manifest 인터넷 연결 / application에서 startKoin
- mainActivity에서 bottomnavi설정 해주고 homefragment와 myfragment를 
  각각 xml  , viewmodel 을 만들어 생성 해준 뒤 연결
- fragment_home 디자인 , (chip , viewpager , style.. , tablelayout)
- RestaurantEntity  -> RestaurantCategory 생성
- RestaurantRepository -> DefaultRestaurantRepository생성
- RestaurantListFragment를 만들고 adapter 생성 후 HomeFragment에 List 나오게 설정
- RestaurantModel 생성해서 RestaurantEntity로 변환 가능한 함수 생성
- RestaurantListFragment에 recyclerview 생성 , viewholder생성 
- Image를 표현하기 위해 extensions패키지에 ImageView , Float 생성 -> home에 restaurantList 보임
- POI데이터 활용해서 내 위치 등록 : 
- HomeState 생성 (uninitialize로 초기화) , 초기화 상태일 때 locationmanager로 위도 경도 설정
- 위도경도를 주소로 받기위해   LocationLatLngEntity , maprepository 생성
- 주소를 받기위해 TMAP API 를 받고 MapApiService 생성
- MapApiService를 사용하기위해 provideMapApiService를 만들고 appmodule에 의존성 주입
- homeFragment에서 state별로 상황 나누고 Success일 때 주소 , UI 보여줌
- 주소클릭해서 변경하기  - MyLocationActivity , ViewModel 생성 후 googlemap 이용해서 
   카메라 이동 시 주소 변경되는 기능 구현
- 기존에 임의의 식당 list를 제거하고 api를 통해 식당 list를 받기
- MapApiService를 이용해 주변 식당 리스트 받기 
- SearchResponse생성 
- 위치 저장 후 사용을 위해 room 을 이용한 db 사용
- db패키지에 dao패키지 생성 후 LocationDao 생성
- 변경된 주소와 기존 room에 저장된 주소를 비교해서 다르면 Toast문구 띄우기
- 카테고리 별로 RestaurantCategory에 입력해서 Tab에 적용시키기
- 기본값 / 배달 빠른순 / 배달 팁 낮은순 / 등 기준을 적용시키기 위해 RestaurantViewModel에 
    enum class인 RestaurantOrder을 만들어서 sort함
- RestaurantDetail을 만들어 식당을 클릭했을 때 세부화면 표시 -> ViewModel과 Activity생성
- RestaurantActivity에서 각종 button과 scroll에 대한 기능 구현
- callButton 함수만들어서 기능 구현
- likedButton을 UserRepository에서 RestaurantDao를 생성한 뒤 좋아요를 눌렀으면 State가 Success
   됐을 때 isLiked를 true로 만든 뒤 db에 식당이름 insert 하고 취소하면 delete해서 false로 구현
- shareButton을 viewmodel에서 restaurantEntity를 받아와 intent처리
- 식당에 대한 상세정보를 위해 RestaurantFoodRepository생성 후 Default에 필요한 함수들 생성
- ApiService설정하고 appmodule에 등록
- RestaurantDetailViewModel에서 restaurantFoodEntity받기
- RestaurantDetailListFragmentAdapter 생성
- 메뉴와 리뷰에 대한 각 fragment와 viewmodel 생성
- RestaurantMenuListViewModel에서 데이터를 받기 위해 food패키지 아래에 FoodModel생성
- FoodMenuViewHolder생성하고 데이터를 연결시킨 뒤 mapper에 입력
- RestaurantMenuListFragment에서 viewmodel에서 받아온 데이터를 화면에 구현
- 메뉴를 장바구니에 담기 위해 FoodMenuBasketDao를 만들어 db사용을 위한 query생성
- 장바구니에 다른 식당의 메뉴가 있는지 확인한 뒤 insert하는 function을 viewmodel에 구현
- 장바구니에 추가하는 로직을 구현하기 위해 RestaurantDetailViewModel과 RestaurantDetailActivity에서 
   다른 식당의 메뉴가 있는지 확인하고 추가하는 함수들을 생성
- HomeFragment에서도 viewmodel에서 data를 받아와 장바구니가 이어지게 설정
- 리뷰 탭  : RestaurantReviewEntity를 만들고 repository를 만들어 getreview를 이용해 viewmodel에서 데이터 연결
   ReviewFragment에서는 임시적으로 Toast가 팝업되게 설정
- reviewModel를 생성해 model 형식의 데이터 받기
- ReviewViewholder를 만들어 reviewadapter를 불러오기 위한 class를 생성
- MyFragment에 대한 UI 구현
- firebase에 google Login 을 연동시켜서 로그인 후 프로필과 이름 화면에 띄우기
- 찜 목록을 만들기 위해 restaurantlikelistFragment / viewModel 생성
(flow에 대해 알아보기)
- 장바구니에 담긴 목록을 주문하기 까지 기능 생성 
<OrderMenuActivity와 ViewModel 생성 후 OrderRepository에서 주문 기능 구현 후 주문 하기를 누르면 FireStore에 메뉴 이름 및 특성들이 저장되게 하기>

