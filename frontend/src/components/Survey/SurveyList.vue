<template>
  <div class="dragula-container">
    <section class="drop-section">
      <div class="section-containers">
        <div class="section-title">
          <div class="survey-title">
            <textarea
              type="text"
              placeholder="설문지 제목"
              v-model="survey.title"
            ></textarea>
          </div>
          <div class="survey-explain">
            <textarea
              type="text"
              placeholder="설문지 설명"
              v-model="survey.explain"
            ></textarea>
          </div>
        </div>
        <div class="section-survey-option">
          <div class="option-st">
            <div class="option-s">
              <div class="option-s-center">
                <input
                  type="radio"
                  name="option-st"
                  value="survey"
                  v-model="surveyOrTemplate"
                />
              </div>
              <p>설문지</p>
            </div>
            <div class="option-t">
              <div class="option-t-center">
                <input
                  type="radio"
                  name="option-st"
                  value="template"
                  v-model="surveyOrTemplate"
                />
              </div>
              <p>탬플릿</p>
            </div>
          </div>
          <div class="option-anony">
            <p class="option-anony-item">실명설문</p>
            <label class="toggle-control">
              <input type="checkbox" checked="checked" @click="isAnony" />
              <span class="control"></span>
            </label>
          </div>
        </div>
        <div id="drop-parent" class="drop-parent"></div>
        <div class="section-control">
          <div class="control-box">
            <button class="create" @click="createSurvey">
              만들기
            </button>
          </div>
        </div>
      </div>
    </section>
    <section class="drag-section">
      <div class="section-containers">
        <div class="sub-button">
          <button
            class="sub-button-q"
            :class="{ 'btn-active': isClkFormMenu }"
            @click="clkFormMenu"
          >
            양식
          </button>
          <button
            class="sub-button-t"
            :class="{ 'btn-active': isClkTemplateMenu }"
            @click="clkTemplateMenu"
          >
            탬플릿
          </button>
        </div>
        <!-- 양식 -->
        <div v-show="isClkFormMenu">
          <div class="sub-title">
            <p class="sub-title-text">드래그해서</p>
            <p class="sub-title-text">설문지를 생성하세요.</p>
          </div>
          <div id="drag-parent" class="drag-parent">
            <div id="SINGLE" class="dragthing-drag">
              <p class="q-type">객관식(단일선택)</p>
              <div class="question">
                <textarea
                  class="question-title"
                  type="text"
                  placeholder="질문제목을 입력해주세요."
                />
                <button class="question-garbage">
                  <i class="fas fa-trash-alt"></i>
                </button>
                <div class="q-option">
                  <div class="q-option-item">
                    <div class="q-option-item-center">
                      <input type="radio" name="SINGLE" value="1" />
                    </div>
                    <input type="text" placeholder="선택" />
                    <i class="fas fa-times" style="display: none;"></i>
                  </div>
                  <div class="q-option-item">
                    <div class="q-option-item-center">
                      <input type="radio" name="SINGLE" value="2" />
                    </div>
                    <input type="text" placeholder="선택" />
                    <i class="fas fa-times"></i>
                  </div>
                  <div class="q-option-item">
                    <div class="q-option-item-center">
                      <input type="radio" name="SINGLE" value="3" />
                    </div>
                    <input type="text" placeholder="선택" />
                    <i class="fas fa-times"></i>
                  </div>
                  <div class="question-control">
                    <p class="q-option-item-c1">'객관식'</p>
                    <p>또는</p>
                    <p class="q-option-item-c2">'주관식' 추가</p>
                  </div>
                </div>
                <div class="is-required">
                  <p class="is-required-text">필수 응답</p>
                  <label class="toggle-control">
                    <input type="checkbox" checked="checked" />
                    <span class="control"></span>
                  </label>
                </div>
              </div>
            </div>
            <div id="MULTIPLE" class="dragthing-drag">
              <p class="q-type">객관식(복수선택)</p>
              <div class="question">
                <textarea
                  class="question-title"
                  type="text"
                  placeholder="질문제목을 입력해주세요."
                />
                <button class="question-garbage">
                  <i class="fas fa-trash-alt"></i>
                </button>
                <div class="q-option">
                  <div class="q-option-item">
                    <div class="q-option-item-center">
                      <input type="checkbox" name="MULTIPLE" value="1" />
                    </div>
                    <input type="text" placeholder="선택" />
                    <i class="fas fa-times" style="display: none;"></i>
                  </div>
                  <div class="q-option-item">
                    <div class="q-option-item-center">
                      <input type="checkbox" name="MULTIPLE" value="2" />
                    </div>
                    <input type="text" placeholder="선택" />
                    <i class="fas fa-times"></i>
                  </div>
                  <div class="q-option-item">
                    <div class="q-option-item-center">
                      <input type="checkbox" name="MULTIPLE" value="3" />
                    </div>
                    <input type="text" placeholder="선택" />
                    <i class="fas fa-times"></i>
                  </div>
                  <div class="question-control">
                    <p class="q-option-item-c1">'객관식'</p>
                    <p>또는</p>
                    <p class="q-option-item-c2">'주관식' 추가</p>
                  </div>
                </div>
                <div class="is-required">
                  <p class="is-required-text">필수 응답</p>
                  <label class="toggle-control">
                    <input type="checkbox" checked="checked" />
                    <span class="control"></span>
                  </label>
                </div>
              </div>
            </div>
            <div id="SHORT" class="dragthing-drag">
              <p class="q-type">주관식</p>
              <div class="question">
                <textarea
                  class="question-title"
                  type="text"
                  placeholder="질문제목을 입력해주세요."
                />
                <button class="question-garbage">
                  <i class="fas fa-trash-alt"></i>
                </button>
                <div class="q-option">
                  <textarea
                    class="item-short"
                    type="text"
                    placeholder="주관식 텍스트"
                  />
                </div>
                <div class="is-required">
                  <p class="is-required-text">필수 응답</p>
                  <label class="toggle-control">
                    <input type="checkbox" checked="checked" />
                    <span class="control"></span>
                  </label>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 탬플릿 -->
        <div v-show="isClkTemplateMenu" style="width: 100%;">
          <div class="sub-title">
            <p class="sub-title-text">탬플릿을 불러와서</p>
            <p class="sub-title-text">설문지를 작성할 수 있습니다.</p>
          </div>
          <div class="template-container">
            <div class="template">
              <div v-if="templates.length != 0">
                <div
                  class="template-item"
                  v-for="(element, index) in templates"
                  :key="index"
                >
                  <div class="item-text">
                    <p class="text-title">
                      {{ element.t_title }}
                    </p>
                    <p class="explanation">{{ element.t_explain }}</p>
                  </div>
                  <button class="load" @click="loadTemplate(index)">
                    <i class="fas fa-file-import fa-lg"></i>
                  </button>
                  <button class="garbage" @click="deleteTemplate(index)">
                    <i class="fas fa-trash-alt fa-lg"></i>
                  </button>
                </div>
              </div>
              <div class="empty-template" v-else>
                <p>생성된 탬플릿이 없습니다.</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import TemplateApi from '@/api/TemplateApi'
import dragula from 'dragula'
import autoScroll from 'dom-autoscroller'
import { singleMake, multipleMake, shortMake } from '@/assets/questionForm'
export default {
  data() {
    return {
      isClkFormMenu: true,
      isClkTemplateMenu: false,
      surveyOrTemplate: null,
      survey: {
        title: null,
        explain: null,
        anony: false, // 익명설문인지
        start_date: null, // surveyset 페이지에서 작성
        end_date: null, // surveyset 페이지에서 작성
        question: [],
        state: 'EXPECTED', // surveyset 페이지에서 보낼 때 결정
        share: [], // surveyset에서
        target: [], // surveyset에서
        complete: [], //빈 배열로
        incomplete: [], // surveyset에서 target과 동일한 값으로
        use_template: null,
        template: null,
        writer: this.$store.state.uid,
      },
      // question: {
      //   q_number: null, // 1, 1-1, 1-2, 2
      //   q_explanation: null,
      //   q_type: null, // SINGLE, MULTIPLE, SHORT
      //   required: true,
      //   q_option: [], // q_type이 SHORT 경우 q_option은 빈 배열
      // },
      // q_option: {
      //   o_number: null, // 문항의의 선택지 번호
      //   o_explanation: null,
      //   short_answer: null, // 객관식의 o_number 선택지가 '기타'냐?
      // },
      templates: [],
      useTemplateIndex: null,
      tempSurveyOption: null,
    }
  },
  methods: {
    loadTemplate(index) {
      this.$swal({
        title: '작성중인 설문지가 존재합니다.',
        icon: 'info',
        target: '.drag-section',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '덮어쓰기',
        cancelButtonText: '취소',
      }).then(result => {
        if (result.isConfirmed) {
          this.useTemplateIndex = index
          console.log(this.templates[index])
          // 뿌려주기 위해
          // 해당 title과 explain 리셋시키고
          console.log(this.templates[index])
          console.log('??', this.survey)
          this.survey.title = this.templates[index]['t_title']
          this.survey.explain = this.templates[index]['t_explain']
          // section-survey-option 리셋만 시킴 anony는 탬플릿에 없음
          document.querySelector('.section-survey-option').remove()
          document
            .querySelector('.section-containers')
            .insertBefore(
              this.tempSurveyOption,
              document.querySelector('.drop-parent'),
            )
          // drag-parent의 childNodes 다 삭제하고,
          let temp2 = document.querySelector('.drop-parent')
          while (temp2.firstChild) {
            temp2.removeChild(temp2.firstChild)
          }
          this.templates[index]['question'].forEach(function(element, index) {
            if (element.q_type == 'SINGLE') {
              // SINGLE일 경우 뿌려주고 그냥 저장된 문자열로 뿌려주고
              // 해당 DOM에 접근해서 데이터 넣어주기
              document
                .querySelector('.drop-parent')
                .insertAdjacentHTML('beforeend', singleMake(element))
            } else if (element.q_type == 'MULTIPLE') {
              // MULTIPLE일 경우 뿌려주고 그냥 저장된 문자열로 뿌려주고
              // 해당 DOM에 접근해서 데이터 넣어주기
              document
                .querySelector('.drop-parent')
                .insertAdjacentHTML('beforeend', multipleMake(element))
            } else {
              // SHORT일 경우 뿌려주고 그냥 저장된 문자열로 뿌려주고
              // 해당 DOM에 접근해서 데이터 넣어주기
              document
                .querySelector('.drop-parent')
                .insertAdjacentHTML('beforeend', shortMake(element))
            }
            // 해당 문항에 대한 값은 다 들어갔다 이제 DOM접근해서 garbage버튼이랑, x버튼이랑, 필수응답 버튼 여부를 활성화 시켜주자.
            let temp = document.querySelector('.drop-parent').childNodes[index]
            temp.childNodes[1].childNodes[1].childNodes[0].addEventListener(
              'click',
              function() {
                temp.remove()
              },
            )
            if (temp.id != 'SHORT') {
              temp.childNodes[1].childNodes[2].childNodes.forEach(function(
                element,
                index,
              ) {
                if (
                  index != 0 &&
                  index !=
                    temp.childNodes[1].childNodes[2].childNodes.length - 1
                ) {
                  element.childNodes[2].addEventListener('click', function() {
                    element.remove()
                  })
                } else if (
                  index ==
                  temp.childNodes[1].childNodes[2].childNodes.length - 1
                ) {
                  element.childNodes[0].addEventListener('click', function() {
                    let cloneChild = temp.childNodes[1].childNodes[2].childNodes[0].cloneNode(
                      true,
                    )
                    cloneChild.childNodes[1].value = ''
                    cloneChild.childNodes[2].style.display = 'block'
                    cloneChild.childNodes[2].addEventListener(
                      'click',
                      function() {
                        cloneChild.remove()
                      },
                    )
                    temp.childNodes[1].childNodes[2].insertBefore(
                      cloneChild,
                      element,
                    )
                  })
                  element.childNodes[2].addEventListener('click', function() {
                    let cloneChild = temp.childNodes[1].childNodes[2].childNodes[0].cloneNode(
                      true,
                    )
                    cloneChild.childNodes[1].setAttribute('placeholder', '기타')
                    cloneChild.childNodes[1].setAttribute('disabled', 'true')
                    cloneChild.childNodes[1].value = ''
                    cloneChild.childNodes[2].style.display = 'block'
                    cloneChild.childNodes[2].addEventListener(
                      'click',
                      function() {
                        cloneChild.remove()
                      },
                    )
                    temp.childNodes[1].childNodes[2].insertBefore(
                      cloneChild,
                      element,
                    )
                  })
                }
              })
            }
            if (!element['required']) {
              temp.childNodes[1].childNodes[3].childNodes[1].childNodes[0].click()
              temp.childNodes[1].childNodes[3].childNodes[1].childNodes[0].classList.add(
                'not-required',
              )
              temp.childNodes[1].childNodes[3].childNodes[1].childNodes[1].classList.add(
                'not-required',
              )
            }
          })
          // toggle 활성화
          // toggle
          document
            .querySelectorAll('.toggle-control')
            .forEach(function(element) {
              element.addEventListener('click', function(e) {
                if (e.target.classList.contains('not-required')) {
                  e.target.classList.remove('not-required')
                } else {
                  e.target.classList.add('not-required')
                }
              })
            })
          // this.survey.template에 tid 넣기
          this.survey.template = this.templates[index].tid
          // use_template에 true 넣기
          this.survey.use_template = true
          document
            .querySelector('.option-s-center > input')
            .addEventListener('click', () => {
              this.surveyOrTemplate = 'survey'
              console.log(this.surveyOrTemplate)
            })
          document
            .querySelector('.option-t-center > input')
            .addEventListener('click', function() {
              this.surveyOrTemplate = 'template'
            })
        }
      })
    },
    deleteTemplate(index) {
      this.$swal({
        title: '탬플릿 삭제를 하시겠습니까?',
        icon: 'warning',
        target: '.drag-section',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '네',
        cancelButtonText: '취소',
      }).then(result => {
        if (result.isConfirmed) {
          const payload = {
            tid: this.templates[index]['tid'],
            uid: this.$store.state.uid,
          }
          TemplateApi.deleteTemplate(
            payload,
            () => {
              this.templates.splice(index, 1)
            },
            () => {},
          )
        }
      })
    },
    createTemplate() {
      TemplateApi.getTemplates(
        this.$store.state.uid,
        res => {
          this.templates = res.data.data
          console.log(this.templates)
        },
        () => {},
      )
    },
    isAnony() {
      this.survey.anony = !this.survey.anony
    },
    createSurvey() {
      if (
        !this.surveyOrTemplate ||
        !this.survey.title ||
        !this.survey.explain
      ) {
        console.log('hi')
        return
      }
      console.log(this.surveyOrTemplate)
      document
        .querySelector('.drop-parent')
        .childNodes.forEach((element1, index1) => {
          let question = {
            q_number: null, // 1, 1-1, 1-2, 2
            q_explanation: null,
            q_type: null, // SINGLE, MULTIPLE, SHORT
            required: true,
            q_option: [], // q_type이 SHORT 경우 q_option은 빈 배열
          }
          console.log('?')
          question.q_number = `${index1 + 1}`
          console.log('?')
          question.q_explanation = `${element1.childNodes[1].childNodes[0].value}`
          console.log('?')
          question.q_type = `${element1.id}`
          console.log('?')
          console.log(element1.childNodes[1].childNodes)
          let minusTemp = 0
          if (element1.childNodes[1].childNodes.length == 4) {
            minusTemp = 1
          }
          console.log(minusTemp)
          if (
            element1.childNodes[1].childNodes[
              4 - minusTemp
            ].childNodes[1].childNodes[0].classList.contains('not-required')
          ) {
            question.required = false
          } else {
            question.required = true
          }
          if (question.q_type != 'SHORT') {
            element1.childNodes[1].childNodes[3 - minusTemp].childNodes.forEach(
              (element2, index2) => {
                let q_option = {
                  o_number: null, // 문항의의 선택지 번호
                  o_explanation: null,
                  short_answer: null, // 객관식의 o_number 선택지가 '기타'냐?
                }
                if (element2.classList.contains('q-option-item')) {
                  if (element2.childNodes[1].placeholder == '선택') {
                    q_option.o_number = `${index2 + 1}`
                    q_option.o_explanation = `${element2.childNodes[1].value}`
                    q_option.short_answer = false
                  } else {
                    q_option.o_number = `${index2 + 1}`
                    q_option.o_explanation = `${element2.childNodes[1].placeholder}`
                    q_option.short_answer = true
                  }
                  question.q_option.push(q_option)
                }
              },
            )
          }
          this.survey.question.push(question)
        })
      if (this.surveyOrTemplate == 'survey') {
        console.log(this.survey)
        console.log('??')
        console.log(this.templates[this.useTemplateIndex])
        // this.survey.use_template이 true이면 다음과 같이
        // true가 아니면 불러온 적이 없으므로  use_template을 false로 해주기
        if (
          !this.survey.use_template &&
          !this.$store.state.surveySet.survey.use_template
        ) {
          this.survey.use_template = false
        }
        // this.survey와 해당 탬플릿들중 useTemplateIndex인 템플릿과
        // title, explain, 실명여부비교
        // q_option들은 문자열로 비교(JSON으로)
        // ||로 비교해주고 같지 않으면 use_template을 false로 해준다.
        let payload = {
          isWriting: true,
          isClkUpdate: false,
          survey: this.survey,
        }
        this.$store.commit('resetSurveyDragThing', [])
        document.querySelector('.drop-parent').childNodes.forEach(element => {
          this.$store.commit('setSurveyDragThing', element.outerHTML)
        })
        this.$store.commit('setSurveySet', payload)
        this.$router.push('/surveyset')
      } else if (this.surveyOrTemplate == 'template') {
        let tempSurvey = JSON.stringify(this.survey)
        tempSurvey = JSON.parse(tempSurvey)
        console.log(tempSurvey)
        tempSurvey['t_title'] = tempSurvey.title
        tempSurvey['t_explain'] = tempSurvey.explain
        delete tempSurvey.title
        delete tempSurvey.explain
        delete tempSurvey.target
        delete tempSurvey.incomplete
        delete tempSurvey.share
        delete tempSurvey.start_date
        delete tempSurvey.end_date
        delete tempSurvey.state
        delete tempSurvey.use_template
        delete tempSurvey.template
        delete tempSurvey.complete
        console.log(tempSurvey)
        TemplateApi.makeTemplate(
          tempSurvey,
          () => {
            let payload = {
              isWriting: false,
              isClkUpdate: false,
              survey: {},
            }
            this.$store.commit('setSurveySet', payload)
            this.templates.unshift(tempSurvey)
            document.querySelector('.sub-button-t').click()
            let temp2 = document.querySelector('.drop-parent')
            while (temp2.firstChild) {
              temp2.removeChild(temp2.firstChild)
            }
            document.querySelector('.section-survey-option').remove()
            document
              .querySelector('.section-containers')
              .insertBefore(
                this.tempSurveyOption,
                document.querySelector('.drop-parent'),
              )
            this.surveyOrTemplate = null
            this.survey.title = null
            this.survey.explain = null
          },
          err => {
            console.log(err)
          },
        )
      }
    },
    clkFormMenu() {
      this.isClkFormMenu = true
      this.isClkTemplateMenu = false
    },
    clkTemplateMenu() {
      this.isClkFormMenu = false
      this.isClkTemplateMenu = true
    },
  },
  mounted() {
    this.tempSurveyOption = document
      .querySelector('.section-survey-option')
      .cloneNode(true)
    if (
      this.$store.getters.getSurveySet.isWriting == true &&
      this.$store.state.surveySet.isClkUpdate == false
    ) {
      this.$swal({
        title: '작성중인 설문지가 존재합니다.',
        text: '이어서 작성하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '계속',
        cancelButtonText: '취소',
      }).then(result => {
        if (result.isConfirmed) {
          this.$router.push('/surveyset')
        } else {
          let payload = {
            survey: {},
            isClkUpdate: false,
            isWriting: false,
          }
          this.$store.commit('resetSurveyDragThing', [])
          this.$store.commit('setSurveySet', payload)
        }
      })
    }
    // 수정클릭시 문항 뿌려주기
    if (this.$store.state.surveySet.isClkUpdate) {
      let dragthings = this.$store.getters.getSurveyDragThing
      dragthings.forEach(function(element) {
        document
          .querySelector('.drop-parent')
          .insertAdjacentHTML('beforeend', element)
      })
      // 제목, 설명
      this.survey.title = this.$store.state.surveySet.survey.title
      this.survey.explain = this.$store.state.surveySet.survey.explain
      // 설문 클릭
      document.querySelector('.option-s-center>input').click()
      // 실명설문인지 아닌지
      if (this.$store.state.surveySet.survey.anony) {
        document.querySelector('.option-anony>label>input').click()
      }
      let allDropEl = document.querySelectorAll('.dragthing-drop')
      let minusTemp = 0
      if (allDropEl[0].childNodes[1].childNodes.length == 4) {
        minusTemp = 1
      }
      this.$store.state.surveySet.survey.question.forEach(function(
        element,
        index,
      ) {
        // 질문제목 넣어줘
        console.log('!!!', allDropEl[index].childNodes[1].childNodes)
        console.log(allDropEl[index])
        allDropEl[index].childNodes[1].childNodes[0].value =
          element.q_explanation
        // 필수 응답인지 아닌지 체크해줘
        if (!element.required) {
          allDropEl[index].childNodes[1].childNodes[
            4 - minusTemp
          ].childNodes[1].childNodes[0].click()
        }
        // garbage-btn 활성화
        allDropEl[index].childNodes[1].childNodes[
          2 - minusTemp
        ].childNodes[0].addEventListener('click', function() {
          allDropEl[index].remove()
        })
        // 객관식 또는 주관식 추가하는거 버튼 활성화시켜줘
        if (element.q_type != 'SHORT') {
          let question =
            allDropEl[index].childNodes[1].childNodes[3 - minusTemp]
          let temp = question.childNodes
          // 기존 태그 삭제 활성화, 기존 input값 넣기
          temp.forEach(function(element2, index) {
            if (index != temp.length - 1) {
              element2.childNodes[1].value =
                element.q_option[index]['o_explanation']
              element2.childNodes[2].addEventListener('click', function() {
                element2.remove()
              })
            }
          })
          // 객관식 버튼 활성화
          temp[temp.length - 1].childNodes[0].addEventListener(
            'click',
            function() {
              let cloneOption = temp[0].cloneNode(true)
              cloneOption.childNodes[1].value = ''
              cloneOption.childNodes[2].style.display = 'block'
              cloneOption.childNodes[2].addEventListener('click', function() {
                cloneOption.remove()
              })
              question.insertBefore(cloneOption, temp[temp.length - 1])
            },
          )
          // 주관식 버튼 활성화
          temp[temp.length - 1].childNodes[2].addEventListener(
            'click',
            function() {
              let cloneOption = temp[0].cloneNode(true)
              cloneOption.childNodes[1].value = ''
              cloneOption.childNodes[2].style.display = 'block'
              cloneOption.childNodes[2].addEventListener('click', function() {
                cloneOption.remove()
              })
              cloneOption.childNodes[1].setAttribute('placeholder', '기타')
              cloneOption.childNodes[1].setAttribute('disabled', 'true')
              question.insertBefore(cloneOption, temp[temp.length - 1])
            },
          )
        }
      })
    }
    // toggle
    document.querySelectorAll('.toggle-control').forEach(function(element) {
      element.addEventListener('click', function(e) {
        if (e.target.classList.contains('not-required')) {
          e.target.classList.remove('not-required')
        } else {
          e.target.classList.add('not-required')
        }
      })
    })
    // dragula contoller
    var drake = dragula(
      [
        document.querySelector('#drag-parent'),
        document.querySelector('#drop-parent'),
      ],
      {
        accepts: function(el, target) {
          if (target.id == 'drag-parent') {
            return false
          } else {
            return true
          }
        },
        revertOnSpill: true,
      },
    )
    drake.on('drop', function(el, target, source) {
      if (target != source) {
        let cloneEl = el.cloneNode(true)
        cloneEl.classList.remove('gu-transit')
        if (el.id == 'SINGLE') {
          document
            .querySelector('#drag-parent')
            .insertBefore(
              cloneEl,
              document.querySelector('#drag-parent>#MULTIPLE'),
            )
        } else if (el.id == 'MULTIPLE') {
          document
            .querySelector('#drag-parent')
            .insertBefore(
              cloneEl,
              document.querySelector('#drag-parent>#SHORT'),
            )
        } else if (el.id == 'SHORT') {
          document.querySelector('#drag-parent').appendChild(cloneEl)
        }
        // garbage-click
        document
          .querySelector(`.dragthing-drag>.question>.question-garbage`)
          .addEventListener('click', function(e) {
            let searchParent = e.target.parentElement
            let check = true
            while (check == true) {
              if (searchParent.classList) {
                if (searchParent.classList.contains('dragthing-drop')) {
                  searchParent.remove()
                  break
                } else {
                  searchParent = searchParent.parentElement
                }
              } else {
                searchParent = searchParent.parentElement
              }
            }
          })
        // 선택or기타 버튼 탐색
        if (el.id != 'SHORT') {
          let controlElement = null
          let qOption = el.childNodes[1].childNodes[3]
          qOption.childNodes.forEach(function(element) {
            if (element.classList.contains('question-control')) {
              controlElement = element
              return false
            }
          })
          // 초기 선택 두개 삭제버튼 활성화
          qOption.childNodes.forEach(function(element) {
            if (element.classList.contains('q-option-item')) {
              element.childNodes[2].addEventListener('click', function() {
                element.remove()
              })
            }
          })
          // 선택추가
          controlElement.childNodes[0].addEventListener('click', function() {
            let cloneqOptionItem = qOption.childNodes[0].cloneNode(true)
            cloneqOptionItem.childNodes[1].value = ''
            cloneqOptionItem.childNodes[2].style.display = 'block'
            cloneqOptionItem.childNodes[2].addEventListener(
              'click',
              function() {
                cloneqOptionItem.remove()
              },
            )
            qOption.insertBefore(cloneqOptionItem, controlElement)
          })
          // 기타추가
          controlElement.childNodes[2].addEventListener('click', function() {
            let cloneqOptionItem = qOption.childNodes[0].cloneNode(true)
            cloneqOptionItem.childNodes[1].value = ''
            cloneqOptionItem.childNodes[2].style.display = 'block'
            cloneqOptionItem.childNodes[2].addEventListener(
              'click',
              function() {
                cloneqOptionItem.remove()
              },
            )
            // input
            cloneqOptionItem.childNodes[1].setAttribute('placeholder', '기타')
            cloneqOptionItem.childNodes[1].setAttribute('disabled', 'true')
            qOption.insertBefore(cloneqOptionItem, controlElement)
          })
        }
        el.classList.remove('dragthing-drag')
        el.classList.add('dragthing-drop')
      }
    })
    autoScroll([window, document.querySelector('#drop-parent')], {
      margin: 20,
      maxSpeed: 15,
      autoScroll: function() {
        return this.down && drake.dragging
      },
    })
  },
  created() {
    this.createTemplate()
  },
  destroyed() {
    this.$store.commit('setIsClkUpdate', false)
  },
}
</script>

<style scroped src="./../../css/survey/survey/survey-list.css"></style>
